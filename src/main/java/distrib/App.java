package distrib;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Controller extends JFrame implements ActionListener {

    public Controller(){
        super();

        JLabel titre = new JLabel("Quelle distribution Linux choisir ? Sélectionne ton profil parmi les suivants :",JLabel.CENTER);
        this.getContentPane().add(titre, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(3,2));

        JButton p1 = new JButton("Développeur");
        p1.addActionListener(this);

        JButton p2 = new JButton("Commercial");
        p2.addActionListener(this);

        JButton p3 = new JButton("Bureautique");
        p3.addActionListener(this);

        JButton p4 = new JButton("Etudiant");
        p4.addActionListener(this);

        JButton p5 = new JButton("Professeur");
        p5.addActionListener(this);

        JButton p6 = new JButton("Etudiant russe");
        p6.addActionListener(this);


        panel.add(p1);
        panel.add(p2);
        panel.add(p3);
        panel.add(p4);
        panel.add(p5);
        panel.add(p6);

        this.getContentPane().add(panel, BorderLayout.CENTER);

        JLabel bottom = new JLabel("Par Scott Rayapoullé et Laurent Toson");
        this.getContentPane().add(bottom, BorderLayout.SOUTH);

        InitializeComponent();
    }

    private void InitializeComponent() {
        this.setTitle("Quelle distribution Linux choisir ? Projet Web Sémantique");
        this.setSize(800,600);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Controller();
    }

    public void actionPerformed(ActionEvent e){

    }
}