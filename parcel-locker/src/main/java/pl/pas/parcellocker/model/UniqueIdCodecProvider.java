package pl.pas.parcellocker.model;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

public class UniqueIdCodecProvider implements CodecProvider {

    public UniqueIdCodecProvider() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Codec<T> get(Class<T> aClass, CodecRegistry codecRegistry) {
        if (aClass == UniqueId.class) {
            return (Codec<T>) new UniqueIdCodec(codecRegistry);
        }
        return null;
    }
}
