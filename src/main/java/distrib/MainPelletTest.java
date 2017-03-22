/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distrib;

import openllet.jena.PelletReasonerFactory;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.riot.RDFDataMgr;

/**
 *
 * @author sharispe
 */
public class MainPelletTest {

    public static void performSPARQLQuery(Model model, String queryString) {

        System.out.println(queryString);
        Query query = QueryFactory.create(queryString);
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect();

            ResultSetFormatter.out(System.out, results, query);
        }

    }

    public static void main(String[] args) {

        String onto_file = System.getProperty("user.dir") + "/resources/seriev7.owl";
        String data_file = System.getProperty("user.dir") + "/resources/datav1.nt";

        Model model = RDFDataMgr.loadModel(onto_file);
        RDFDataMgr.read(model, data_file);

        //https://jena.apache.org/documentation/query/app_api.html
        String queryA = "SELECT ?Serie WHERE { ?Serie a <http://www.ema.com/ontologies/series#Serie> . ?Serie <http://www.ema.com/ontologies/series#isAiredOn> <http://www.ema.com/ontologies/series#Web> }";
        String queryB = "SELECT ?Serie WHERE { ?Serie a <http://www.ema.com/ontologies/series#Serie-Web> } ";
        String queryC = "SELECT ?Serie ?s WHERE { ?Serie <http://www.ema.com/ontologies/series#nbSaisons> ?s }";
        String queryD = "SELECT ?Serie WHERE { ?Serie a <http://www.ema.com/ontologies/series#Mini-Serie> }";
        String queryE = "SELECT ?Serie WHERE { ?Serie a <http://www.ema.com/ontologies/series#LongSeries> }";

        // reasoner using Jena
        System.out.println("-------------------------------------");
        System.out.println("-               JENA                -");
        System.out.println("-------------------------------------");
        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        InfModel inf = ModelFactory.createInfModel(reasoner, model);

        performSPARQLQuery(inf, queryA);
        performSPARQLQuery(inf, queryB);
        performSPARQLQuery(inf, queryC);
        performSPARQLQuery(inf, queryD);
        performSPARQLQuery(inf, queryE);

        // reasoning using Openllet
        System.out.println("-------------------------------------");
        System.out.println("-             OPENLLET              -");
        System.out.println("-------------------------------------");
        Reasoner reasonerPellet = PelletReasonerFactory.theInstance().create();
        InfModel infOpenllet = ModelFactory.createInfModel(reasonerPellet, model);

        performSPARQLQuery(infOpenllet, queryA);
        performSPARQLQuery(infOpenllet, queryB);
        performSPARQLQuery(infOpenllet, queryC);
        performSPARQLQuery(infOpenllet, queryD);
        performSPARQLQuery(infOpenllet, queryE);

    }

}
