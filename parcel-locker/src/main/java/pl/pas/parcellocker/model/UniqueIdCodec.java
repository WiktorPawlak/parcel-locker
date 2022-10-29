package pl.pas.parcellocker.model;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.UUID;

public class UniqueIdCodec implements Codec<UniqueId> {

    private Codec<UUID> uuidCodec;

    public UniqueIdCodec(CodecRegistry registry) {
        uuidCodec = registry.get(UUID.class);
    }

    @Override
    public UniqueId decode(BsonReader bsonReader, DecoderContext decoderContext) {
        UUID uuid = uuidCodec.decode(bsonReader, decoderContext);
        return new UniqueId(uuid);
    }

    @Override
    public void encode(BsonWriter bsonWriter, UniqueId uuid, EncoderContext encoderContext) {
        uuidCodec.encode(bsonWriter, uuid.getUUID(), encoderContext);
    }

    @Override
    public Class<UniqueId> getEncoderClass() {
        return UniqueId.class;
    }
}
