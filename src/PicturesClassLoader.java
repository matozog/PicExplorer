import java.awt.Image;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PicturesClassLoader extends ClassLoader {

	private Class<?> clas;
	
	public PicturesClassLoader()
	{
		
	}
	
	public Object loadPictureClass(String nameClass, String methodName, String arg)
	{
		Object object = null;
		Image image = null;
		try {
			clas = this.loadClass(nameClass);
			resolveClass(clas);
			Constructor<?> constructor = clas.getConstructor(String.class);
			object = constructor.newInstance(arg);
			Method method =  object.getClass().getMethod(methodName);
			image = (Image) method.invoke(object);
		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException
				| NoSuchMethodException | SecurityException | InvocationTargetException e1) {
			e1.printStackTrace();
		}
		return image;
	}
}
