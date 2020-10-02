package StringsReader;

import ru.pflb.mq.dummy.exception.DummyException;
import ru.pflb.mq.dummy.implementation.ConnectionImpl;
import ru.pflb.mq.dummy.interfaces.Destination;
import ru.pflb.mq.dummy.interfaces.Producer;
import ru.pflb.mq.dummy.interfaces.Session;

import java.io.*;
import java.util.*;

public class StringsReader {
    public static void main(String[] args) throws DummyException, InterruptedException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader
                (new FileInputStream(args[0])));
             ConnectionImpl ci = new ConnectionImpl();
             ) {
            ci.start();
            Session session =  ci.createSession(true);
            Destination dest = session.createDestination("Queue");
            Producer producer = session.createProducer(dest);


            List<String> list = new ArrayList<>();
            String c = br.readLine();

            while (c !=null) {
                list.add(c);
                c = br.readLine();
            }
            while (true) {
                for (String s:list) {
                    Thread.sleep(2000);
                    producer.send(s);
                }
            }
            //ci.close();
        } catch (IOException io) {
            System.out.println("Ошибка при чтении файла!");
        }
    }
}