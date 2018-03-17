import java.awt.Image;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PicturesClassLoader extends ClassLoader {

	private Class clas;
	
	public PicturesClassLoader()
	{
		
	}
	
	public Object loadPictureClass(String nameClass, String methodName, String arg)
	{
		Object object = null;
		Image image = null;
		try {
			clas = this.loadClass(nameClass);
			object = clas.newInstance();
			Method method =  object.getClass().getMethod(methodName,String.class);
			image = (Image) method.invoke(object, arg);
		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException
				| NoSuchMethodException | SecurityException | InvocationTargetException e1) {
			e1.printStackTrace();
		}
		return image;
	}
}
