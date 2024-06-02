package org.example.response;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.example.model.User;

public class JsonDeserializer {

    <T> T readFromString(String json, Class<T> targetClass) throws InvocationTargetException, InstantiationException,
                                                                   IllegalAccessException {

//        {
//            "login": "abc",
//            "email": "abd@mail.ru",
//            "roles": [
//            {"type": "ADMIN", "createdAt": "2022-01-01"},
//            {"type": "USER", "createdAt": "2020-01-01"}
//           ]
//        }

//        JsonObject obj = JsonObject.parse(json);
//        obj.getNode("login") -> Node ( "abc")
//        obj.roles("roles") -> ArrayNode ( ...)
        // у Jackson есть такая, можно посмотреть


        Field[] fields = targetClass.getFields();// внутри могут быть примитивы и ВЛОЖЕННЫЕ классы

        Constructor<?> defaultConstructor = Arrays.stream(targetClass.getDeclaredConstructors())
                .filter(con -> con.getParameterCount() == 0)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("No default constructor exists for class " + targetClass.getName()));
        defaultConstructor.setAccessible(true);
        T myObject = (T) defaultConstructor.newInstance();


        for (Field field : fields) { // можно то же самое сделать через сеттеры
            Object value = jsonObject.getNode(field.getName()); // берем field.getName, ищем такой name в json, берем у него value
            // если это pojo -> сначала deserialize()
            field.set(myObject, value);
        }

        // если поля final
        Constructor<?> fullConstructor = Arrays.stream(targetClass.getDeclaredConstructors())
//                .filter(con -> con.getParameters().equals( [login , email, roles] ))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("No default constructor exists for class " + targetClass.getName()));
        // roles это объект, так что создаем его отдельно
//      T myObject = (T) fullConstructor.newInstance(login, email, roles);

        return myObject;
    }
}
