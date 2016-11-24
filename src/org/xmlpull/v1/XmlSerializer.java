package org.xmlpull.v1;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 *
 * @author Gregery Barton
 */
public interface XmlSerializer {

	/**
	 * Set feature identified by name (recommended to be URI for uniqueness). Some well known optional features are defined
	 * in http://www.xmlpull.org/v1/doc/features.html. If feature is not recognized or can not be set then
	 * IllegalStateException MUST be thrown.
	 *
	 * @param string
	 * @param bln
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 */
	void setFeature(String string, boolean bln) throws IllegalArgumentException, IllegalStateException;

	/**
	 * Return the current value of the feature with given name.
	 *
	 * NOTE: unknown properties are always returned as null
	 *
	 * @param string
	 * @return
	 */
	boolean getFeature(String string);

	/**
	 * Set the value of a property. (the property name is recommended to be URI for uniqueness). Some well known optional
	 * properties are defined in http://www.xmlpull.org/v1/doc/properties.html. If property is not recognized or can not be
	 * set then IllegalStateException MUST be thrown.
	 *
	 * @param string
	 * @param o
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 */
	void setProperty(String string, Object o) throws IllegalArgumentException, IllegalStateException;

	/**
	 * Look up the value of a property. The property name is any fully-qualified URI. I
	 *
	 * NOTE: unknown properties are always returned as null
	 *
	 * @param string
	 * @return
	 */
	Object getProperty(String string);

	/**
	 *
	 * @param out
	 * @param string
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 */
	void setOutput(OutputStream out, String string) throws IOException, IllegalArgumentException,
		IllegalStateException;

	/**
	 *
	 * Set to use binary output stream with given encoding.
	 *
	 * @param writer
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 */
	void setOutput(Writer writer) throws IOException, IllegalArgumentException, IllegalStateException;

	/**
	 * Write <? xml declaration with encoding (if encoding not null) and standalone flag (if standalone not null) This
	 * method can only be called just after setOutput. @param string @param bln @throws IOException @throws
	 * IllegalArgumentException @throws IllegalStateException
	 * @param string
	 * @param bln
	 * @throws java.io.IOException
	 */
	void startDocument(String string, Boolean bln) throws IOException, IllegalArgumentException,
		IllegalStateException;

	/**
	 * Finish writing. All unclosed start tags will be closed and output will be flushed. After calling this method no more
	 * output can be serialized until next call to setOutput()
	 *
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 */
	void endDocument() throws IOException, IllegalArgumentException, IllegalStateException;

	/**
	 * Binds the given prefix to the given namespace. This call is valid for the next element including child elements. The
	 * prefix and namespace MUST be always declared even if prefix is not used in element (startTag() or attribute()) - for
	 * XML 1.0 it must result in declaring xmlns:prefix='namespace' (or xmlns:prefix="namespace" depending what character
	 * is used to quote attribute value).
	 *
	 * NOTE: this method MUST be called directly before startTag() and if anything but startTag() or setPrefix() is called
	 * next there will be exception.
	 *
	 * NOTE: prefixes "xml" and "xmlns" are already bound and can not be redefined see: Namespaces in XML Errata.
	 *
	 * NOTE: to set default namespace use as prefix empty string.
	 *
	 * @param string
	 * @param string1
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 */
	void setPrefix(String string, String string1) throws IOException, IllegalArgumentException,
		IllegalStateException;

	/**
	 * Return namespace that corresponds to given prefix If there is no prefix bound to this namespace return null but if
	 * generatePrefix is false then return generated prefix.
	 *
	 * NOTE: if the prefix is empty string "" and default namespace is bound to this prefix then empty string ("") is
	 * returned.
	 *
	 * NOTE: prefixes "xml" and "xmlns" are already bound will have values as defined Namespaces in XML specification
	 *
	 * @param string
	 * @param bln
	 * @return
	 * @throws IllegalArgumentException
	 */
	String getPrefix(String string, boolean bln) throws IllegalArgumentException;

	/**
	 * Returns the current depth of the element. Outside the root element, the depth is 0. The depth is incremented by 1
	 * when startTag() is called. The depth is decremented after the call to endTag() event was observed.
	 *
	 * @return
	 */
	int getDepth();

	/**
	 * Returns the namespace URI of the current element as set by startTag().
	 *
	 * NOTE: that means in particular that:
	 *
	 * if there was startTag("", ...) then getNamespace() returns "" if there was startTag(null, ...) then getNamespace()
	 * returns null
	 *
	 * @return
	 */
	String getNamespace();

	/**
	 * Returns the name of the current element as set by startTag(). It can only be null before first call to startTag() or
	 * when last endTag() is called to close first startTag().
	 *
	 * @return
	 */
	String getName();

	/**
	 * Writes a start tag with the given namespace and name. If there is no prefix defined for the given namespace, a
	 * prefix will be defined automatically. The explicit prefixes for namespaces can be established by calling setPrefix()
	 * immediately before this method. If namespace is null no namespace prefix is printed but just name. If namespace is
	 * empty string then serializer will make sure that default empty namespace is declared (in XML 1.0 xmlns='') or throw
	 * IllegalStateException if default namespace is already bound to non-empty string.
	 *
	 * @param string
	 * @param string1
	 * @return
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 */
	XmlSerializer startTag(String string, String string1) throws IOException, IllegalArgumentException,
		IllegalStateException;

	/**
	 * Write an attribute. Calls to attribute() MUST follow a call to startTag() immediately. If there is no prefix defined
	 * for the given namespace, a prefix will be defined automatically. If namespace is null or empty string no namespace
	 * prefix is printed but just name.
	 *
	 * @param string
	 * @param string1
	 * @param string2
	 * @return
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 */
	XmlSerializer attribute(String string, String string1, String string2) throws IOException,
		IllegalArgumentException, IllegalStateException;

	/**
	 * Write end tag. Repetition of namespace and name is just for avoiding errors.
	 *
	 * Background: in kXML endTag had no arguments, and non matching tags were very difficult to find... If namespace is
	 * null no namespace prefix is printed but just name. If namespace is empty string then serializer will make sure that
	 * default empty namespace is declared (in XML 1.0 xmlns=''). * @param string
	 *
	 * @param string
	 * @param string1
	 * @return
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 */
	XmlSerializer endTag(String string, String string1) throws IOException, IllegalArgumentException,
		IllegalStateException;

	/**
	 * Writes text, where special XML chars are escaped automatically
	 *
	 * @param string
	 * @return
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 */
	XmlSerializer text(String string) throws IOException, IllegalArgumentException, IllegalStateException;

	/**
	 * Writes text, where special XML chars are escaped automatically
	 *
	 * @param chars
	 * @param i
	 * @param i1
	 * @return
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 */
	XmlSerializer text(char[] chars, int i, int i1) throws IOException, IllegalArgumentException,
		IllegalStateException;

	/**
	 *
	 * @param string
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 */
	void cdsect(String string) throws IOException, IllegalArgumentException, IllegalStateException;

	/**
	 *
	 * @param string
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 */
	void entityRef(String string) throws IOException, IllegalArgumentException, IllegalStateException;

	/**
	 *
	 * @param string
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 */
	void processingInstruction(String string) throws IOException, IllegalArgumentException, IllegalStateException;

	/**
	 *
	 * @param string
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 */
	void comment(String string) throws IOException, IllegalArgumentException, IllegalStateException;

	/**
	 *
	 * @param string
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 */
	void docdecl(String string) throws IOException, IllegalArgumentException, IllegalStateException;

	/**
	 *
	 * @param string
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 */
	void ignorableWhitespace(String string) throws IOException, IllegalArgumentException, IllegalStateException;

	/**
	 * Write all pending output to the stream. If method startTag() or attribute() was called then start tag is closed
	 * (final >) before flush() is called on underlying output stream.
	 *
	 * NOTE: if there is need to close start tag (so no more attribute() calls are allowed) but without flushing output
	 * call method text() with empty string (text("")).
	 *
	 * @throws IOException
	 */
	void flush() throws IOException;

}
