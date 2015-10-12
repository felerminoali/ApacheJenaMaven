/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mz.com.jena.main;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.util.FileManager;
//import org.apache.jena.

/**
 *
 * @author feler
 */
public class Main {

    public static void main(String[] args) {

//        FileManager.get().addLocatorClassLoader(Main.class.getClassLoader());
//        Model model = FileManager.get().loadModel("src/main/resources/data.rdf");
//
//        model.write(System.out, "TURTLE");
        sparqTest();
        
    }

    public static void sparqTest() {
        FileManager.get().addLocatorClassLoader(Main.class.getClassLoader());
        Model model = FileManager.get().loadModel("src/main/resources/data.rdf");

        String queryString
                = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX foaf: <http://xmlns.com/foaf/0.1/>"
                + "SELECT * WHERE {"
                + " ?person foaf:name ?x ."
//                + "FILTER(?x = \"Charles\")"
//                + " ?person foaf:knows ?person2 ."
//                + " ?person2 foaf:name ?y ."
//                + "FILTER(?y = \"Charles\")"
                + "}";

        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                Literal name = soln.getLiteral("x");
                System.out.println(name);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            qexec.close();
        }

    }

}
