package com.Amanda;

import java.util.List;

public interface IConversor {

    <T> T conversorObjetos (String json, Class<T> classe);

    <T> List<T> conversorLista (String json, Class<T> classe);
}
