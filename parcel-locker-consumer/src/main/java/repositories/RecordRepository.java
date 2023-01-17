package repositories;

import model.ModelRecord;


public class RecordRepository extends AbstractMongoRepository<ModelRecord> {

    public RecordRepository() {
        super("records", ModelRecord.class);
    }

}
