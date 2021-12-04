package com.digitalinnovationone.heroesapi.service;


import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.digitalinnovationone.heroesapi.document.Heroes;
import com.digitalinnovationone.heroesapi.repository.HeroesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DynamoService {

    private DynamoDBMapper dynamoDBMapper;
    public static final String TABLE_NAME="Heroes_Table_Bruno";

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    HeroesRepository repository;


    public void listTables() {

        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

        CreateTableRequest tableRequest = dynamoDBMapper
                .generateCreateTableRequest(Heroes.class);
        tableRequest.setProvisionedThroughput(
                new ProvisionedThroughput(1L, 1L));
        System.out.println("Listando tabelas");
        ListTablesResult tables = amazonDynamoDB.listTables(TABLE_NAME);

//        if(tables.)

        for (String name : tables.getTableNames()) {
            System.out.println("Table name: " + name);

        }
        System.out.println("ANTES de criar tabelas");
        amazonDynamoDB.createTable(tableRequest);
        System.out.println("Pós criação tabelas");
    }

}