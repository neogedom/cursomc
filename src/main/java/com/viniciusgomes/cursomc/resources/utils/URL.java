package com.viniciusgomes.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// A classe URL é uma classe utilitária para tratamento de urls de requisições REST
public class URL {

    // Esse método foi criado para separar a lista de categorias que vem na URL no formato String
    // e devolver essa lista no formato List<Integer>
    public static List<Integer> decodeIntList (String s) {
//        String [] vet = s.split(",");
//        List<Integer> list = new ArrayList<>();
//
//        for (int i=0; i<vet.length; i++) {
//            list.add(Integer.parseInt(vet[i]));
//        }
//
//        return list;

        return Arrays.asList(s.split(",")).stream()
                .map(x -> Integer.parseInt(x)).collect(Collectors.toList());
    }


    public static String decodeParam(String s) {
        // O método decode de URLDecoder faz a decodificação do encode automático que o Javascript faz
        // ao submter uma string com espaços por intermédio de um formulário
        try {
            return URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // Caso alguma exceção aconteça no processo de decodificar, retorno uma String vazia para
            // ser inserida no search
            return "";
        }
    }
}
