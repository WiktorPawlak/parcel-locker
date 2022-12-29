package pl.pas.parcellocker.repositories.codec;

import com.datastax.oss.driver.api.core.data.UdtValue;
import com.datastax.oss.driver.api.core.type.UserDefinedType;
import com.datastax.oss.driver.api.core.type.codec.MappingCodec;
import com.datastax.oss.driver.api.core.type.codec.TypeCodec;
import com.datastax.oss.driver.api.core.type.reflect.GenericType;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import pl.pas.parcellocker.model.DepositBox;

public class DepositBoxCodec extends MappingCodec<UdtValue, DepositBox> {

    public DepositBoxCodec(@NonNull TypeCodec<UdtValue> innerCodec) {
        super(innerCodec, GenericType.of(DepositBox.class));
    }

    @NonNull @Override public UserDefinedType getCqlType() {
        return (UserDefinedType) super.getCqlType();
    }

    @Nullable
    @Override
    protected DepositBox innerToOuter(@Nullable UdtValue udtValue) {
        return udtValue == null ? null : new DepositBox(
            udtValue.getUuid("entity_id"),
            udtValue.getUuid("delivery_id"),
            udtValue.getBoolean("is_empty"),
            udtValue.getString("access_code"),
            udtValue.getString("tel_number")
        );
    }

    @Nullable
    @Override
    protected UdtValue outerToInner(@Nullable DepositBox depositBox) {
        return depositBox == null ? null : getCqlType().newValue()
            .setUuid("entity_id", depositBox.getEntityId())
            .setUuid("delivery_id", depositBox.getDeliveryId())
            .setBoolean("is_empty", depositBox.isEmpty())
            .setString("access_code", depositBox.getAccessCode())
            .setString("tel_number", depositBox.getTelNumber());
    }
}
