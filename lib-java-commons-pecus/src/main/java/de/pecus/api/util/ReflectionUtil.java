package de.pecus.api.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.pecus.api.constant.GeneralConstants;

/**
 * Clase de utiler&iacute;a para utilizar los m&eacute;todos de reflexi&oacute;n.
 *
 * @author Luis Enrique Sanchez Santiago
 */
public final class ReflectionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger( ReflectionUtil.class );

    /** Se ofusca el contructor */
    private ReflectionUtil() {
        // No debe instanciarse
    }

    /**
     * Establece el valor de una propiedad por medio de reflexi&oacute;n
     *
     * @param value Valor a settear
     * @param fieldName El nombre del atributo (
     * @param myInstance
     */
    public static void set( Object value, String fieldName, Object myInstance ) {
        try{
            Class<?> clazz = getClassFor( myInstance, fieldName );
            Field field = findField( clazz, fieldName );
            if( field != null ){
                makeAccessible( field );
                field.set( myInstance, value );
            }
        }
        catch( SecurityException | IllegalArgumentException | IllegalAccessException e){
            LOGGER.error( e.getMessage(), e );
        }

    }

    /**
     * Obtiene el valor del atributo dado el nombre y la instancia de la clase
     *
     * @param fieldName
     * @param myInstance
     * @return
     */
    public static Object get( String fieldName, Object myInstance ) {
        Object value = null;
        try{

            Class<?> clazz = getClassFor( myInstance, fieldName );
            Field field = findField( clazz, fieldName );
            if(!ValidatorUtil.isNull(field)) {
                makeAccessible(field);
                value = field.get( myInstance );
            }
        }
        catch( SecurityException | IllegalArgumentException | IllegalAccessException e ){
            LOGGER.error( e.getMessage(), e );
        }

        return value;
    }

    @SuppressWarnings("rawtypes")
    private static Class getClassFor( Object myInstance, String fieldName ) {
        Class clazz = myInstance.getClass();

        while( true ){

            Field[] fields = clazz.getClass().getDeclaredFields();
            fields = ArrayUtils.addAll( fields, clazz.getClass().getSuperclass().getDeclaredFields() );
            for( Field field : fields ){
                if( field.getName().equals( fieldName ) ){
                    break;
                }
            }
            break;
        }

        return clazz;
    }

    /**
     * Makes a {@link java.lang.reflect.Field} accessible
     *
     * @param field
     */
    public static void makeAccessible( Field field ) {
        if( ((Modifier.isPublic( field.getModifiers() ))
                && (Modifier.isPublic( field.getDeclaringClass().getModifiers() )) && (!(Modifier.isFinal( field
                .getModifiers() )))) || (field.isAccessible()) ){
            return;
        }
        field.setAccessible( true );
    }

    /**
     * Makes a {@link java.lang.reflect.Method} accessible
     *
     * @param method
     */
    public static void makeAccessible( Method method ) {
        if( ((Modifier.isPublic( method.getModifiers() )) && (Modifier.isPublic( method.getDeclaringClass()
                .getModifiers() ))) || (method.isAccessible()) ){
            return;
        }
        method.setAccessible( true );
    }

    /**
     * Makes a {@link java.lang.reflect.Constructor<?>} accessible
     *
     * @param ctor
     */
    public static void makeAccessible( Constructor<?> ctor ) {
        if( ((Modifier.isPublic( ctor.getModifiers() )) && (Modifier.isPublic( ctor.getDeclaringClass().getModifiers() )))
                || (ctor.isAccessible()) ){
            return;
        }
        ctor.setAccessible( true );
    }

    /**
     * Search a {@link java.lang.reflect.Field} given its class and name
     *
     * @param clazz
     * @param name
     * @return
     */
    public static Field findField( Class<?> clazz, String name ) {
        return findField( clazz, name, null );
    }

    /**
     * Search a {@link java.lang.reflect.Field} given its class and name
     *
     * @param clazz
     * @param name
     * @param type
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Field findField( Class<?> clazz, String name, Class<?> type ) {
        validateField( clazz, name, type );
        Class searchType = clazz;
        while( (!(Object.class.equals( searchType ))) && (searchType != null) ){
            Field[] fields = searchType.getDeclaredFields();
            for( Field field : fields ){
                if( ((name == null) || (name.equals( field.getName() ))) && ((type == null) || (type.equals( field.getType() ))) ){
                    return field;
                }
            }
            searchType = searchType.getSuperclass();
        }
        return null;
    }

    /**
     * Search a {@link java.lang.reflect.Field} given its class
     *
     * @param clazz
     * @param name
     * @param type
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static List<Field> findFields( Class<?> clazz, Class<?> type ) {
        List<Field> fieldsString = new ArrayList<>();
        Class searchType = clazz;
        while( (!(Object.class.equals( searchType ))) && (searchType != null) ){
            Field[] fields = searchType.getDeclaredFields();
            for( Field field : fields ){
                if( (type == null) || type.equals( field.getType() ) ){
                    fieldsString.add( field );
                }
            }
            searchType = searchType.getSuperclass();
        }
        return fieldsString;
    }

    private static void validateField( Class<?> clazz, String name, Class<?> type ) {
        if( clazz == null ){
            throw new IllegalArgumentException( "Class must not be null" );
        }
        if( !((name != null) || (type != null)) ){
            throw new IllegalArgumentException( "Either name or type of the field must be specified" );
        }
    }

    /**
     * Gets the value of the field from a given object
     *
     * @param field
     * @param target
     * @return
     */
    public static Object getField( Field field, Object target ) {
        try{
            return field.get( target );
        }
        catch( IllegalAccessException ex ){
            handleReflectionException( ex );
            throw new IllegalStateException( "Unexpected reflection exception - " + ex.getClass().getName() + ": "
                    + ex.getMessage() );
        }
    }

    /**
     * Handles the reflection exceptions
     *
     * @param ex
     */
    public static void handleReflectionException( Exception ex ) {
        if( ex instanceof NoSuchMethodException ){
            throw new IllegalStateException( "Method not found: " + ex.getMessage() );
        }
        if( ex instanceof IllegalAccessException ){
            throw new IllegalStateException( "Could not access method: " + ex.getMessage() );
        }
        if( ex instanceof InvocationTargetException ){
            handleInvocationTargetException( (InvocationTargetException) ex );
        }
        if( ex instanceof RuntimeException ){
            throw ((RuntimeException) ex);
        }
        throw new UndeclaredThrowableException( ex );
    }

    /**
     * Handles the reflection exceptions
     *
     * @param ex
     */
    private static void handleInvocationTargetException( InvocationTargetException ex ) {
        rethrowRuntimeException( ex.getTargetException() );
    }

    /**
     * Handles the reflection exceptions
     *
     * @param ex
     */
    private static void rethrowRuntimeException( Throwable ex ) {
        if( ex instanceof RuntimeException ){
            throw ((RuntimeException) ex);
        }
        if( ex instanceof Error ){
            throw ((Error) ex);
        }
        throw new UndeclaredThrowableException( ex );
    }

    /**
     * Method that finds a method in a given class.
     *
     * @param clazz {@link Class} that owns the method.
     * @param name The name of the method.
     * @return A {@link Method} object representing the method found.
     */
    public static Method findMethod( Class<?> clazz, String name ) {
        return findMethod( clazz, name, new Class[0] );
    }

    /**
     * Method that finds a method with parameters in a given class.
     *
     * @param clazz {@link Class} that owns the method.
     * @param name The name of the method.
     * @param paramTypes Array with the parameters classes.
     * @return A {@link Method} object representing the method found.
     */
    @SuppressWarnings("rawtypes")
    public static Method findMethod( Class<?> clazz, String name, Class<?>[] paramTypes ) {
        Class searchType = clazz;
        while( searchType != null ){
            Method[] methods = (searchType.isInterface()) ? searchType.getMethods() : searchType.getDeclaredMethods();
            for( Method method : methods ){
                if( (name.equals(method.getName()) ) && ((paramTypes == null) || (Arrays.equals( paramTypes, method.getParameterTypes())) ) ){
                    return method;
                }
            }
            searchType = searchType.getSuperclass();
        }
        return null;
    }

    /**
     * Method that invokes a method in a given object.
     *
     * @param method {@link Method} object representing the method.
     * @param target Reference to the object that owns the method.
     * @return An {@link Object} with the method return object.
     */
    public static Object invokeMethod( Method method, Object target ) {
        return invokeMethod( method, target, new Object[0] );
    }

    /**
     * Method that invokes a method with parameters in a given object.
     *
     * @param method {@link Method} object representing the method.
     * @param target Reference to the object that owns the method.
     * @param args Array of arguments for the method.
     * @return An {@link Object} with the method return object.
     */
    public static Object invokeMethod( Method method, Object target, Object[] args ) {
        try{
            return method.invoke( target, args );
        }
        catch( Exception ex ){
            handleReflectionException( ex );

            throw new IllegalStateException( "Should never get here" );
        }
    }

    /**
     * Metodo para copiar los valores de las propiedades de bean source al bean target para todos los casos en los que
     * los nombres de las propiedades son el mismo.
     *
     * @param source Bean fuente.
     * @param target Bean destino.
     * @author vn0wosm
     */
    public static void copyProperties( Object source, Object target ) {
        try{
            BeanUtils.copyProperties( target, source );
        }
        catch( IllegalAccessException | InvocationTargetException e ){
            LOGGER.error( e.getMessage(), e );
        }
    }

    /**
     * Get the method name for a depth in call stack. <br />
     * Utility function
     *
     * @param depth
     */
    public static String getMethodName(final int depth) {
        final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        return ste[depth].getMethodName();
    }

    /**
     * Get the method name for a controller in call stack. <br />
     * Utility function
     *
     */
    public static String getControllerMethodName() {
        return getMethodName(GeneralConstants.CONTROLLER_LAYER_DEPTH);
    }

}