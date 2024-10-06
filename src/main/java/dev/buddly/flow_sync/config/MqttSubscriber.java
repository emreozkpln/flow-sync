package dev.buddly.flow_sync.config;

import dev.buddly.flow_sync.model.Product;
import dev.buddly.flow_sync.model.Store;
import dev.buddly.flow_sync.repository.ProductRepository;
import dev.buddly.flow_sync.repository.StoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;


@Slf4j
public class MqttSubscriber {

    private final String brokerUrl = "";
    private final String clientId = "";
    private final String topic = "";


    public MqttSubscriber(ProductRepository productRepository, StoreRepository storeRepository) {
        try {
            IMqttClient mqttClient = new MqttClient(brokerUrl, clientId, new MemoryPersistence());
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            mqttClient.connect(connOpts);

            mqttClient.subscribe(topic, (topic, message) -> {
                String payload = new String(message.getPayload(), StandardCharsets.UTF_8);
                log.info("Received message: " + payload + " on topic " + topic);
                System.out.println("Received message: " + payload + " on topic " + topic);
                StringBuilder cardId = getCardId(payload);
                StringBuilder batteryLevel = getBatteryLevel(payload);

                String[] topicData = getTopicData(topic);
                StringBuilder storeId = new StringBuilder(topicData[0]);
                StringBuilder lat = new StringBuilder(topicData[1]);
                StringBuilder lng = new StringBuilder(topicData[2]);

                Store existingStore = storeRepository.findByStoreId(String.valueOf(storeId));
                if (existingStore == null) {
                    Store newStore = new Store();
                    newStore.setStoreId(String.valueOf(storeId));
                    newStore.setStoreName(String.valueOf(storeId));
                    newStore.setLat(String.valueOf(lat));
                    newStore.setLng(String.valueOf(lng));
                    storeRepository.save(newStore);
                }

                Store addedStore = storeRepository.findByStoreId(String.valueOf(storeId));
                ZonedDateTime turkiyeZamani = ZonedDateTime.now(ZoneId.of("Europe/Istanbul"));
                LocalDateTime currentTime = turkiyeZamani.toLocalDateTime();
                Product existingProduct = productRepository.findByProductId(String.valueOf(cardId));

                if ( existingProduct != null){
                    LocalDateTime lastReadingTime = existingProduct.getReadingTime();

                    LocalDateTime date1 = LocalDateTime.parse(currentTime.toString());
                    LocalDateTime date2 = LocalDateTime.parse(lastReadingTime.toString());
                    Duration duration = Duration.between(date2, date1);
                    long minutesDifference = duration.toMinutes();
                    long days = duration.toDays();
                    long hours = duration.toHours() % 24;
                    long minutes = duration.toMinutes() % 60;
                    String formattedDifference = String.format("%d day %d hour %d minutes", days, hours, minutes);
                    System.out.println("Difference: " + formattedDifference);

                    existingProduct.setPassingTime(formattedDifference);
                    existingProduct.setBatteryLevel(String.valueOf(batteryLevel));
                    existingProduct.setReadingTime(currentTime);
                    productRepository.save(existingProduct);

                    if (minutesDifference >= 40) {
                        existingProduct.setStat("2");
                        productRepository.save(existingProduct);
                    }
                }else{
                    Product newProduct = new Product();
                    newProduct.setProductId(String.valueOf(cardId));
                    newProduct.setBatteryLevel(String.valueOf(batteryLevel));
                    newProduct.setReadingTime(currentTime);
                    newProduct.setStore(addedStore);
                    newProduct.setStat("1");
                    productRepository.save(newProduct);
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    public static StringBuilder getCardId(String payload) {
        StringBuilder cardId = new StringBuilder();
        String[] hextoArray = payload.split(",");
        for (int i = 0; i < 5; i++) {
            cardId.append(hextoArray[i]);
        }
        return cardId;
    }
    public static StringBuilder getBatteryLevel(String payload) {
        StringBuilder batteryLevel = new StringBuilder();
        String[] hextoArray = payload.split(",");
        String hexValue = hextoArray[hextoArray.length - 1];
        int decimalValue = Integer.parseInt(hexValue, 16);
        batteryLevel.append(decimalValue);
        return batteryLevel;
    }

    public static String[] getTopicData(String topic) {
        String veriParcasi = topic.substring(topic.indexOf("/") + 1);
        String[] hextoArray = veriParcasi.split(",");
        return hextoArray;
    }

}
