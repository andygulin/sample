package examples.showcase.serializable;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import examples.showcase.User;

public class AvroTest extends BaseTest {

	@Test
	public void avro() throws IOException, ReflectiveOperationException {
		// 序列化
		Resource resource = new ClassPathResource("examples/showcase/serializable/user.avro");
		Schema schema = new Schema.Parser().parse(resource.getFile());
		GenericRecord record = new GenericData.Record(schema);
		toRecord(record, user);

		File file = new File(FileUtils.getTempDirectoryPath(), "user.ser");
		OutputStream os = FileUtils.openOutputStream(file);
		DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<GenericRecord>(schema);
		DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<GenericRecord>(datumWriter);
		dataFileWriter.create(schema, os);
		dataFileWriter.append(record);
		dataFileWriter.close();

		// 反序列化
		DatumReader<GenericRecord> datumReader = new GenericDatumReader<GenericRecord>(schema);
		DataFileReader<GenericRecord> dataFileReader = new DataFileReader<GenericRecord>(file, datumReader);
		GenericRecord user = null;
		while (dataFileReader.hasNext()) {
			user = dataFileReader.next(user);
			System.out.println(user);
		}
		dataFileReader.close();
	}

	private void toRecord(GenericRecord record, User user) throws ReflectiveOperationException {
		List<Field> fields = FieldUtils.getAllFieldsList(User.class);
		Iterator<Field> iter = fields.iterator();
		while (iter.hasNext()) {
			Field field = iter.next();
			int mod = field.getModifiers();
			if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
				iter.remove();
			}
		}
		for (Field field : fields) {
			Class<?> classType = field.getType();
			String fieldName = field.getName();
			if (classType == Date.class) {
				record.put(fieldName, ((Date) PropertyUtils.getProperty(user, fieldName)).getTime());
			} else {
				record.put(fieldName, PropertyUtils.getProperty(user, fieldName));
			}
		}
	}
}
