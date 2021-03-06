package com.digitalinnovationone.heroesapi.service;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.digitalinnovationone.heroesapi.document.Heroes;
import com.digitalinnovationone.heroesapi.repository.HeroesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.digitalinnovationone.heroesapi.constants.HeroesConstant.ENDPOINT_DYNAMO;
import static com.digitalinnovationone.heroesapi.constants.HeroesConstant.REGION_DYNAMO;

@Service
public class HeroesService {

    @Autowired
    private HeroesRepository heroesRepository;

    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    public Flux<Heroes> findAll(){

        return Flux.fromIterable(this.heroesRepository.findAll());
    }

    public Mono<Heroes> findById(String id){

        return  Mono.justOrEmpty(this.heroesRepository.findById(id));
    }


    public Mono<Heroes> save(Heroes heroes){
        return  Mono.justOrEmpty(this.heroesRepository.save(heroes));
    }


    public Mono<Boolean> deletebyIDHero(String id) {
        heroesRepository.deleteById(id);
        return Mono.just(true);

    }

    public Flux<Void> listTables(){
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(ENDPOINT_DYNAMO, REGION_DYNAMO))
                .build();

        DynamoDB dynamoDB = new DynamoDB(client);
        dynamoDB.listTables().forEach(
                t -> System.out.println(t.getDescription())
        );

        return Flux.empty();
    }

    public Mono<Void> createTable() {

        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

        CreateTableRequest tableRequest = dynamoDBMapper
                .generateCreateTableRequest(Heroes.class);
        tableRequest.setProvisionedThroughput(
                new ProvisionedThroughput(1L, 1L));


        amazonDynamoDB.createTable(tableRequest);
        return Mono.empty();
    }



}
