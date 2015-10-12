/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mz.com.jena.main;

import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasonerFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.PrintUtil;
import org.apache.jena.vocabulary.ReasonerVocabulary;

/**
 *
 * @author feler
 */
public class JenaRules {

    public static void main(String[] args) {
        // create a basic RAW model that can do no inferencing
// create a basic RAW model that can do no inferencing
        Model rawModel = FileManager.get().loadModel("src/main/resources/rolloRdfs.n3");

        // create an InfModel that will infer new facts.
        InfModel infmodel = ModelFactory.createRDFSModel(rawModel);

        Resource rollo = infmodel.getResource("http://www.codesupreme.com/#rollo");
        Resource marla = infmodel.getResource("http://www.codesupreme.com/#marla");

        Model postRuleModel = processRules("src/main/resources/rules.txt", infmodel);

        printStatements(postRuleModel, marla, null, null);
        System.out.println("< -- Jena Done -->");

    }

    public static void printStatements(Model m, Resource s, Property p, Resource o) {
        PrintUtil.registerPrefix("x", "http://www.codesupreme.com/#");
        for (StmtIterator i = m.listStatements(s, p, o); i.hasNext();) {
            Statement stmt = i.nextStatement();
            System.out.println(" - " + PrintUtil.print(stmt));
        }
    }

    public static Model processRules(String fileloc, InfModel modelIn) {
        // create a simple model; create a resource and add rules from a file

        Model m = ModelFactory.createDefaultModel();
        Resource configuration = m.createResource();
        configuration.addProperty(ReasonerVocabulary.PROPruleSet, fileloc);

        // Create an instance of a reasoner
        Reasoner reasoner
                = GenericRuleReasonerFactory.theInstance().create(configuration);

        // Now with the rawdata model & the reasoner, create an InfModel
        InfModel infmodel = ModelFactory.createInfModel(reasoner, modelIn);

        // use the following to show everything that can be deduced based on the rules
        //infmodel.setNsPrefix("drc", "http://www.codesupreme.com/#");
        //infmodel.write(System.out, "N3");
        return infmodel;
    }

}
