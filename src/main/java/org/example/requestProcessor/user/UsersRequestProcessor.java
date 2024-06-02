package org.example.requestProcessor.user;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import jakarta.ws.rs.core.Response;
import org.example.exception.MethodNotAllowedException;
import org.example.exception.NoContentException;
import org.example.httpRequest.HttpRequest;
import org.example.model.User;
import org.example.requestProcessor.ReqProcessor;
import org.example.response.ResponseProcessor;
import org.example.service.UserService;

public class UsersRequestProcessor implements ReqProcessor {

    private final UserService userService;
    private final ResponseProcessor responseProcessor;

    public UsersRequestProcessor(UserService userService, ResponseProcessor responseProcessor) {
        this.userService = userService;
        this.responseProcessor = responseProcessor;
    }

    @Override
    public void process(HttpRequest httpRequest) {
        switch (httpRequest.getMethod()) {
            case GET -> getRequest(httpRequest);
            case POST -> postRequest(httpRequest);
            default -> throw new MethodNotAllowedException();
        }
    }

    private void getRequest(HttpRequest httpRequest) {
        List<User> userList = userService.getAllUsers();

        if (!userList.isEmpty()) {
            responseProcessor.returnBody(httpRequest, Response.Status.OK, userList);
        } else {
            responseProcessor.returnNoBody(httpRequest, Response.Status.NO_CONTENT);
            throw new NoContentException();
        }
    }

    private void postRequest(HttpRequest httpRequest) throws IllegalAccessException {
        MyDeserializer des = null;
        User userToCreate = des.parse(httpRequest.getBody(), User.class);

        User myUser = null;
//        {
//            "login": "abc",
//                "email": "abd@mail.ru",
//                "roles": [
//            {"type": "ADMIN", "createdAt": "abc"},
//            {"type": "USER", "createdAt": "2020-01-01"}
//          ]
//        }
        MySerializer ser = null;
//        ser.preferredType = FIELD/METHOD
        ser.writeAsString(myUser);





        /// cache - последняя очередь, после того как все заработает
        // без кеша - 10 мс
        // с кешем - 1 мс

        StringBuilder sb = new StringBuilder();
        Class<? extends User> userClass = myUser.getClass();
        String rootTag = userClass.getSimpleName();

        sb.append("<").append(rootTag).append(">");


        Field[] fields = userClass.getFields();
//        userClass.getMethods()
                // getName -> "name": "vasya"
                // isActive -> "active": true
        Map<String, FieldGetter> fieldNameToGetter = new HashMap<>();
        for (Field field : fields) {
            field.setAccessible(true);
            fieldNameToGetter.put(field.getName(), field::get);
        }

        // ()()[]()
        // ((([])[][]))
        // (([)])
        // { "dssdf": "fds", "nestedObjects": [{...}, {...}] }
        // String -> "abc"
        // Number -> 123
        // Boolean -> true/false

        for (Map.Entry<String, FieldGetter> entry : fieldNameToGetter.entrySet()) {
            String name = entry.getKey();
            FieldGetter getter = entry.getValue();
            Object value = getter.getValue(myUser);
            sb.append(name);
            // value - примитив - пишем
            sb.append(value);
            // value - pojo -
            sb.append(ser.writeAsString(value));
        }


        // мы можем 1 раз посчитать
        Map<Class<?>, <rootTag,fieldNameToGetter> > classToHowToSerialize = new HashMap<>();




//        if (httpRequest.hadBody()) {
//            MyDeserializer des = null;
//            User userForm = des.parse(httpRequest.getBody(), User.class);
//            usersService.addUser(userForm);
//        } else {
            // There is no body to create User.
            // Return response that no body was received.
//        }
    }

    interface FieldGetter {
        Object getValue(Object owner) throws IllegalAccessException;
    }

//    GET domain.com/my-api/users -> список юзеров
//    POST domain.com/my-api/users + body -> создать нового
//    GET domain.com/my-api/users/{userID} -> получить юзера
//    POST domain.com/my-api/users/{userId}/block + no body -> заблокировать юзера
}
