package com.digitalinnovationone.heroesapi.document;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;


import lombok.Data;
import lombok.NoArgsConstructor;

import static com.digitalinnovationone.heroesapi.constants.HeroesConstant.TABLE_NAME;

@Data
@AllArgsConstructor
@DynamoDBTable(tableName = TABLE_NAME)
public class Heroes {

    @Id
    @DynamoDBHashKey (attributeName = "id")
    private String id;

    @DynamoDBAttribute (attributeName = "name")
    private String name;

    @DynamoDBAttribute (attributeName = "universe")
    private String universe;

    @DynamoDBAttribute (attributeName = "films")
    private Long films;


}