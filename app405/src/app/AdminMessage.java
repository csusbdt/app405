package app;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;

public class AdminMessage
{
	private static final String entityKind = "AdminMessage";
	private static final String textPropertyName = "text";
	private static final long id = 1;
	
	private Entity entity = null;

	private AdminMessage(Entity entity)
	{
		this.entity = entity;
	}

	public String getText()
	{
		return ((Text) entity.getProperty(textPropertyName)).getValue();
	}

	private void setText(String text)
	{
		entity.setProperty(textPropertyName, new Text(text));
	}

	private void save()
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		datastore.put(entity);
	}

	public static AdminMessage createOrUpdate(String text)
	{
		AdminMessage message = getAdminMessage();
		message.setText(text);
		message.save();
		return message;
	}

	public static AdminMessage getAdminMessage()
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key key = KeyFactory.createKey(entityKind, id);
		Entity entity = null;
		try
		{
			entity = datastore.get(key);
		}
		catch (EntityNotFoundException e)
		{
			entity = new Entity(entityKind, id);
			entity.setProperty(textPropertyName, new Text("default message"));
			AdminMessage message = new AdminMessage(entity);
			message.save();
			return message;
		}
		return new AdminMessage(entity);
	}
}
