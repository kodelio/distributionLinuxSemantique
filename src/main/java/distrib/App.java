package distrib;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.util.iterator.ExtendedIterator;


public class App extends JFrame implements ActionListener {

    public static String onto_file = System.getProperty("user.dir") + "/resources/projet_web_semantique.owl";
    public static String NS = "http://www.infres9.org/rayapoulle/toson/2017/distributionLinux#";

    public static void performSPARQLQuery(Model model, String queryString) {
        System.out.println(queryString);
        Query query = QueryFactory.create(queryString);
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect();

            ResultSetFormatter.out(System.out, results, query);
        }
    }

    public App(){
        super();

        JLabel titre = new JLabel("Quelle distribution Linux choisir ? Sélectionne ton profil parmi les suivants :",JLabel.CENTER);
        this.getContentPane().add(titre, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(4,2));
        JPanel panelBottom = new JPanel(new GridLayout(2,1));
        JPanel panelBottom1 = new JPanel(new BorderLayout());
        JPanel panelBottom2 = new JPanel(new GridLayout(3,4));

        OntModel model = ModelFactory.createOntologyModel();
        model.read(onto_file);

        ExtendedIterator classes = model.listClasses();
        while (classes.hasNext())
        {
            OntClass thisClass = (OntClass) classes.next();
            if (thisClass.toString().contains("Profil")) {
                ExtendedIterator subClasses = thisClass.listSubClasses();
                ArrayList<String> profiles = new ArrayList<>();
                while (subClasses.hasNext())
                {
                    OntClass thisSubClass = (OntClass) subClasses.next();
                    profiles.add(thisSubClass.toString().replace(NS, ""));
                }
                for (String st : profiles) {
                    JButton jb = new JButton(st);
                    jb.addActionListener(this);
                    panel.add(jb);
                }
            }
            else if (thisClass.toString().contains("Distribution")) {
                ExtendedIterator instances = thisClass.listInstances();
                ArrayList<String> distribs = new ArrayList<>();
                while (instances.hasNext())
                {
                    Individual thisInstance = (Individual) instances.next();
                    distribs.add(thisInstance.toString().replace(NS, ""));
                }
                JLabel allDistrib = new JLabel(" Toutes les distributions :");
                panelBottom1.add(allDistrib);
                for (String st : distribs) {
                    JButton jb = new JButton(st);
                    panelBottom2.add(jb);
                }
            }
        }

        panelBottom.add(panelBottom1);
        panelBottom.add(panelBottom2);

        this.getContentPane().add(panel, BorderLayout.CENTER);
        this.getContentPane().add(panelBottom, BorderLayout.SOUTH);

        InitializeComponent();
    }

    private void InitializeComponent() {
        this.setTitle("Distribution Linux - Projet Web Sémantique - Par Scott Rayapoullé et Laurent Toson");
        this.setSize(800,600);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new App();
    }

    public void actionPerformed(ActionEvent e){
        JButton obj = (JButton) e.getSource();
        System.out.println(obj.getText());

        Model model = RDFDataMgr.loadModel(onto_file);

        //https://jena.apache.org/documentation/query/app_api.html
        String queryA = "SELECT ?distribution WHERE { ?distribution <"+ NS +"baseeSur>  'Debian' }";
        System.out.println(queryA);

        performSPARQLQuery(model, queryA);

        // reasoner
        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        InfModel inf = ModelFactory.createInfModel(reasoner, model);

        performSPARQLQuery(inf, queryA);
    }
}