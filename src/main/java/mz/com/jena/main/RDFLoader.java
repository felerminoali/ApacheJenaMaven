/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mz.com.jena.main;

import java.util.ArrayList;
import java.util.List;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.util.FileManager;

/**
 *
 * @author feler
 */
public class RDFLoader {

    public List<String> sparqTest(String queryString) {
        FileManager.get().addLocatorClassLoader(RDFLoader.class.getClassLoader());
        List<String> nameList = new ArrayList<String>();
        try {
//            Model model = FileManager.get().loadModel("src/main/resources/data.rdf");
            Model model = FileManager.get().loadModel("http://localhost:8080/mavenproject2/data.rdf");
            

            Query query = QueryFactory.create(queryString);
            QueryExecution qexec = QueryExecutionFactory.create(query, model);
            try {
                ResultSet results = qexec.execSelect();
                while (results.hasNext()) {
                    QuerySolution soln = results.nextSolution();
                    Literal name = soln.getLiteral("x");

                    nameList.add(name.getString());
                }
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                qexec.close();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return nameList;

    }

}
