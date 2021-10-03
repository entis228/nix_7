package com.entis;

import com.entis.csvparser.components.Table;
import com.entis.csvparser.exception.CSVMappingException;
import com.entis.csvparser.exception.CSVParsingException;
import com.entis.csvparser.mappers.impl.MapperImpl;
import com.entis.csvparser.parsers.impl.FileParser;
import com.entis.entity.User;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        args=new String[1];
        args[0]="input.csv";
        if(args[0]!=null){
            try {
                Path filePath= Paths.get(args[0]);
                FileParser fileParser=new FileParser();
                Table table=fileParser.parse(filePath);
                MapperImpl mapper=new MapperImpl();
                List<User>usersInFile=mapper.map(table,User.class);
                usersInFile.forEach(System.out::println);
            } catch (CSVParsingException | CSVMappingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
