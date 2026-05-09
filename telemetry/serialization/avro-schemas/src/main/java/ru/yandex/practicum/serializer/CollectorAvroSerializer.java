package ru.yandex.practicum.serializer;

import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.serialization.Serializer;

import java.io.ByteArrayOutputStream;

//@SuppressWarnings("unused")
public class CollectorAvroSerializer implements Serializer<SpecificRecordBase> {

    @Override
    public byte[] serialize(String topic, SpecificRecordBase input) throws SerializationException {
        if (input == null) {
            return null;
        }
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            DatumWriter<SpecificRecordBase> datumWriter = new SpecificDatumWriter<>(input.getSchema());

            BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(outputStream, null);

            datumWriter.write(input, encoder);

            encoder.flush();

            outputStream.close();

            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new SerializationException("Ошибка сериализации экземпляра по topic:" + topic);
        }
    }
}