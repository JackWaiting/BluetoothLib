package spp.bluetooth.jackwaiting.lib.utils;

import android.util.Log;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * <strong>A simple wrapper class of {@link Log},which supplies
 * more useful information and is easier to use than {@link Log}
 * .</strong>
 * </p>
 * 
 * @author JackWaiting
 * 
 * @since 0.0.1
 */
public final class LogManager
{
	/**
	 * The debug tag for you to show the log or not.You must modify the tag
	 * manually!
	 * 
	 * @since 0.0.1
	 */
	public static  boolean DEBUG = true;

	/**
	 * The default tag for the log.
	 * 
	 * @since 0.0.1
	 */
	private static String sTagDefault = LogManager.class.getSimpleName();

	/**
	 * Set the default tag for the global environment.
	 * 
	 * @param tagDefault
	 *            The default tag you want to set,no matter null or empty.
	 * 
	 * @since 0.0.1
	 */
	public static void setTagDefault(String tagDefault)
	{
		sTagDefault = dealWithString(tagDefault);
	}

	/**
	 * Get the default tag.
	 * 
	 * @return The default tag.
	 * 
	 * @since 0.0.1
	 */
	public static String getTagDefault()
	{
		return sTagDefault;
	}

	/**
	 * It's the same as you use {@link Log#v(String, String)},the
	 * tag is {@link LogManager#sTagDefault}.
	 * 
	 * @param message
	 *            The message you want to log,no matter it's null or empty.
	 * 
	 * @since 0.0.1
	 */
	public static void v(String message)
	{
		if (DEBUG)
		{
			log(Level.V, sTagDefault, buildMessage(message).toString().trim(),
					null);
		}
	}

	/**
	 * It's the same as you use {@link Log#v(String, String)}.
	 * 
	 * @param tag
	 *            The tag you want to set,no matter it's null or empty.
	 * @param message
	 *            The message you want to log,no matter it's null or empty.
	 * 
	 * @since 0.0.1
	 */
	public static void v(String tag, String message)
	{
		if (DEBUG)
		{
			log(Level.V, tag, buildMessage(message).toString().trim(), null);
		}
	}

	/**
	 * It's the same as you use
	 * {@link Log#v(String, String, Throwable)}.
	 * 
	 * @param tag
	 *            The tag you want to set,no matter it's null or empty.
	 * @param message
	 *            The message you want to log,no matter it's null or empty.
	 * @param throwable
	 *            {@link Throwable} .
	 * 
	 * @since 0.0.1
	 */
	public static void v(String tag, String message, Throwable throwable)
	{
		if (DEBUG)
		{
			log(Level.V, tag, buildMessage(message).toString().trim(),
					throwable);
		}
	}

	/**
	 * It's the same as you use {@link Log#d(String, String)},the
	 * tag is {@link LogManager#sTagDefault}.
	 * 
	 * @param message
	 *            The message you want to log,no matter it's null or empty.
	 * 
	 * @since 0.0.1
	 */
	public static void d(String message)
	{
		if (DEBUG)
		{
			log(Level.D, sTagDefault, buildMessage(message).toString().trim(),
					null);
		}
	}

	/**
	 * It's the same as you use {@link Log#d(String, String)}.
	 * 
	 * @param tag
	 *            The tag you want to set,no matter it's null or empty.
	 * @param message
	 *            The message you want to log,no matter it's null or empty.
	 * 
	 * @since 0.0.1
	 */
	public static void d(String tag, String message)
	{
		if (DEBUG)
		{
			log(Level.D, tag, buildMessage(message).toString().trim(), null);
		}
	}

	/**
	 * It's the same as you use
	 * {@link Log#d(String, String, Throwable)}.
	 * 
	 * @param tag
	 *            The tag you want to set,no matter it's null or empty.
	 * @param message
	 *            The message you want to log,no matter it's null or empty.
	 * @param throwable
	 *            {@link Throwable} .
	 * 
	 * @since 0.0.1
	 */
	public static void d(String tag, String message, Throwable throwable)
	{
		if (DEBUG)
		{
			log(Level.D, tag, buildMessage(message).toString().trim(),
					throwable);
		}
	}

	/**
	 * It's the same as you use {@link Log#i(String, String)},the
	 * tag is {@link LogManager#sTagDefault}.
	 * 
	 * @param message
	 *            The message you want to log,no matter it's null or empty.
	 * 
	 * @since 0.0.1
	 */
	public static void i(String message)
	{
		if (DEBUG)
		{
			log(Level.I, sTagDefault, buildMessage(message).toString().trim(),
					null);
		}
	}

	/**
	 * It's the same as you use {@link Log#i(String, String)}.
	 * 
	 * @param tag
	 *            The tag you want to set,no matter it's null or empty.
	 * @param message
	 *            The message you want to log,no matter it's null or empty.
	 * 
	 * @since 0.0.1
	 */
	public static void i(String tag, String message)
	{
		if (DEBUG)
		{
			log(Level.I, tag, buildMessage(message).toString().trim(), null);
		}
	}

	/**
	 * It's the same as you use
	 * {@link Log#i(String, String, Throwable)}.
	 * 
	 * @param tag
	 *            The tag you want to set,no matter it's null or empty.
	 * @param message
	 *            The message you want to log,no matter it's null or empty.
	 * @param throwable
	 *            {@link Throwable} .
	 * 
	 * @since 0.0.1
	 */
	public static void i(String tag, String message, Throwable throwable)
	{
		if (DEBUG)
		{
			log(Level.I, tag, buildMessage(message).toString().trim(),
					throwable);
		}
	}

	/**
	 * It's the same as you use {@link Log#i(String, String)},the
	 * tag is {@link LogManager#sTagDefault}.
	 * 
	 * @param message
	 *            The message you want to log,no matter it's null or empty.
	 * 
	 * @since 0.0.1
	 */
	public static void w(String message)
	{
		if (DEBUG)
		{
			log(Level.W, sTagDefault, buildMessage(message).toString().trim(),
					null);
		}
	}

	/**
	 * It's the same as you use {@link Log#w(String, String)}.
	 * 
	 * @param tag
	 *            The tag you want to set,no matter it's null or empty.
	 * @param message
	 *            The message you want to log,no matter it's null or empty.
	 * 
	 * @since 0.0.1
	 */
	public static void w(String tag, String message)
	{
		if (DEBUG)
		{
			log(Level.W, tag, buildMessage(message).toString().trim(), null);
		}
	}

	/**
	 * It's the same as you use
	 * {@link Log#w(String, String, Throwable)}.
	 * 
	 * @param tag
	 *            The tag you want to set,no matter it's null or empty.
	 * @param message
	 *            The message you want to log,no matter it's null or empty.
	 * @param throwable
	 *            {@link Throwable} .
	 * 
	 * @since 0.0.1
	 */
	public static void w(String tag, String message, Throwable throwable)
	{
		if (DEBUG)
		{
			log(Level.W, tag, buildMessage(message).toString().trim(),
					throwable);
		}
	}

	/**
	 * It's the same as you use {@link Log#e(String, String)},the
	 * tag is {@link LogManager#sTagDefault}.
	 * 
	 * @param message
	 *            The message you want to log,no matter it's null or empty.
	 * 
	 * @since 0.0.1
	 */
	public static void e(String message)
	{
		if (DEBUG)
		{
			log(Level.E, sTagDefault, buildMessage(message).toString().trim(),
					null);
		}
	}

	/**
	 * It's the same as you use {@link Log#e(String, String)}.
	 * 
	 * @param tag
	 *            The tag you want to set,no matter it's null or empty.
	 * @param message
	 *            The message you want to log,no matter it's null or empty.
	 * 
	 * @since 0.0.1
	 */
	public static void e(String tag, String message)
	{
		if (DEBUG)
		{
			log(Level.E, tag, buildMessage(message).toString().trim(), null);
		}
	}

	/**
	 * It's the same as you use
	 * {@link Log#e(String, String, Throwable)}.
	 * 
	 * @param tag
	 *            The tag you want to set,no matter it's null or empty.
	 * @param message
	 *            The message you want to log,no matter it's null or empty.
	 * @param throwable
	 *            {@link Throwable} .
	 * 
	 * @since 0.0.1
	 */
	public static void e(String tag, String message, Throwable throwable)
	{
		if (DEBUG)
		{
			log(Level.E, tag, buildMessage(message).toString().trim(),
					throwable);
		}
	}

	/**
	 * It's the same as you use {@link Log#wtf(String, String)},the
	 * tag is {@link LogManager#sTagDefault}.
	 * 
	 * @param message
	 *            The message you want to log,no matter it's null or empty.
	 * 
	 * @since 0.0.1
	 */
	public static void wtf(String message)
	{
		if (DEBUG)
		{
			log(Level.WTF, sTagDefault,
					buildMessage(message).toString().trim(), null);
		}
	}

	/**
	 * It's the same as you use {@link Log#wtf(String, String)}.
	 * 
	 * @param tag
	 *            The tag you want to set,no matter it's null or empty.
	 * @param message
	 *            The message you want to log,no matter it's null or empty.
	 * 
	 * @since 0.0.1
	 */
	public static void wtf(String tag, String message)
	{
		if (DEBUG)
		{
			log(Level.WTF, tag, buildMessage(message).toString().trim(), null);
		}
	}

	/**
	 * It's the same as you use
	 * {@link Log#wtf(String, String, Throwable)}.
	 * 
	 * @param tag
	 *            The tag you want to set,no matter it's null or empty.
	 * @param message
	 *            The message you want to log,no matter it's null or empty.
	 * @param throwable
	 *            {@link Throwable} .
	 * 
	 * @since 0.0.1
	 */
	public static void wtf(String tag, String message, Throwable throwable)
	{
		if (DEBUG)
		{
			log(Level.WTF, tag, buildMessage(message).toString().trim(),
					throwable);
		}
	}

	/**
	 * Log the objects with the level V.
	 * 
	 * @param objects
	 *            The objects you want to log,no matter it's null or empty.
	 * 
	 * @since 0.0.1
	 */
	public static void verbose(Object... objects)
	{
		if (DEBUG)
		{
			log(Level.V, sTagDefault, buildMessage(objects).toString().trim(),
					null);
		}
	}

	/**
	 * Log the objects with the level V.
	 * 
	 * @param tag
	 *            The tag you want to set,no matter it's null or empty.
	 * @param objects
	 *            The objects you want to log,no matter it's null or empty.
	 * 
	 * @since 0.0.1
	 */
	public static void verbose(String tag, Object... objects)
	{
		if (DEBUG)
		{
			log(Level.V, tag, buildMessage(objects).toString().trim(), null);
		}
	}

	/**
	 * Log the objects with the level D.
	 * 
	 * @param objects
	 *            The objects you want to log,no matter it's null or empty.
	 * 
	 * @since 0.0.1
	 */
	public static void debug(Object... objects)
	{
		if (DEBUG)
		{
			log(Level.D, sTagDefault, buildMessage(objects).toString().trim(),
					null);
		}
	}

	/**
	 * Log the objects with the level D.
	 * 
	 * @param tag
	 *            The tag you want to set,no matter it's null or empty.
	 * @param objects
	 *            The objects you want to log,no matter it's null or empty.
	 * 
	 * @since 0.0.1
	 */
	public static void debug(String tag, Object... objects)
	{
		if (DEBUG)
		{
			log(Level.D, tag, buildMessage(objects).toString().trim(), null);
		}
	}

	/**
	 * Log the objects with the level I.
	 * 
	 * @param objects
	 *            The objects you want to log,no matter it's null or empty.
	 * 
	 * @since 0.0.1
	 */
	public static void info(Object... objects)
	{
		if (DEBUG)
		{
			log(Level.I, sTagDefault, buildMessage(objects).toString().trim(),
					null);
		}
	}

	/**
	 * Log the objects with the level I.
	 * 
	 * @param tag
	 *            The tag you want to set,no matter it's null or empty.
	 * @param objects
	 *            The objects you want to log,no matter it's null or empty.
	 * 
	 * @since 0.0.1
	 */
	public static void info(String tag, Object... objects)
	{
		if (DEBUG)
		{
			log(Level.I, sTagDefault, buildMessage(objects).toString().trim(),
					null);
		}
	}

	/**
	 * Log the objects with the level W.
	 * 
	 * @param objects
	 *            The objects you want to log,no matter it's null or empty.
	 * 
	 * @since 0.0.1
	 */
	public static void warn(Object... objects)
	{
		if (DEBUG)
		{
			log(Level.W, sTagDefault, buildMessage(objects).toString().trim(),
					null);
		}
	}

	/**
	 * Log the objects with the level W.
	 * 
	 * @param tag
	 *            The tag you want to set,no matter it's null or empty.
	 * @param objects
	 *            The objects you want to log,no matter it's null or empty.
	 * 
	 * @since 0.0.1
	 */
	public static void warn(String tag, Object... objects)
	{
		if (DEBUG)
		{
			log(Level.W, tag, buildMessage(objects).toString().trim(), null);
		}
	}

	/**
	 * Log the objects with the level E.
	 * 
	 * @param objects
	 *            The objects you want to log,no matter it's null or empty.
	 * 
	 * @since 0.0.1
	 */
	public static void error(Object... objects)
	{
		if (DEBUG)
		{
			log(Level.E, sTagDefault, buildMessage(objects).toString().trim(),
					null);
		}
	}

	/**
	 * Log the objects with the level E.
	 * 
	 * @param tag
	 *            The tag you want to set,no matter it's null or empty.
	 * @param objects
	 *            The objects you want to log,no matter it's null or empty.
	 * 
	 * @since 0.0.1
	 */
	public static void error(String tag, Object... objects)
	{
		if (DEBUG)
		{
			log(Level.E, tag, buildMessage(objects).toString().trim(), null);
		}
	}

	/**
	 * Log the objects with the level WTF.
	 * 
	 * @param objects
	 *            The objects you want to log,no matter it's null or empty.
	 * 
	 * @since 0.0.1
	 */
	public static void wtf(Object... objects)
	{
		if (DEBUG)
		{
			log(Level.WTF, sTagDefault,
					buildMessage(objects).toString().trim(), null);
		}
	}

	/**
	 * Log the objects with the level WTF.
	 * 
	 * @param tag
	 *            The tag you want to set,no matter it's null or empty.
	 * @param objects
	 *            The objects you want to log,no matter it's null or empty.
	 * 
	 * @since 0.0.1
	 */
	public static void wtf(String tag, Object... objects)
	{
		if (DEBUG)
		{
			log(Level.WTF, tag, buildMessage(objects).toString().trim(), null);
		}
	}

	/**
	 * Build the more useful message.
	 * 
	 * @param objects
	 *            The objects you want to log.
	 * @return The more useful message.
	 */
	private static StringBuffer buildMessage(Object... objects)
	{
		StringBuffer message = new StringBuffer();

		message.append("****************************************************")
				.append("\n");

		message.append(buildStackTraceInformation(new Throwable()
				.getStackTrace()));

		if ((objects != null) && (objects.length > 0))
		{
			for (int i = 0; i < objects.length; i++)
			{
				if (objects[i] != null)
				{
					if (objects[i] instanceof boolean[])
					{
						boolean[] objecters = (boolean[]) objects[i];
						message.append("{");

						for (int j = 0; j < objecters.length; j++)
						{
							message.append(objecters[j]);

							if (j != (objecters.length - 1))
							{
								message.append(" ");
							}
						}

						message.append("}");
					}
					else if (objects[i] instanceof char[])
					{
						char[] objecters = (char[]) objects[i];
						message.append("{");

						for (int j = 0; j < objecters.length; j++)
						{
							message.append(objecters[j]);

							if (j != (objecters.length - 1))
							{
								message.append(" ");
							}
						}

						message.append("}");
					}
					else if (objects[i] instanceof byte[])
					{
						byte[] objecters = (byte[]) objects[i];
						message.append("{");

						for (int j = 0; j < objecters.length; j++)
						{
                            message.append(Integer.toHexString(objecters[j]&0xFF));

							if (j != (objecters.length - 1))
							{
								message.append(" ");
							}
						}

						message.append("}");
					}
					else if (objects[i] instanceof short[])
					{
						short[] objecters = (short[]) objects[i];
						message.append("{");

						for (int j = 0; j < objecters.length; j++)
						{
							message.append(objecters[j]);

							if (j != (objecters.length - 1))
							{
								message.append(" ");
							}
						}

						message.append("}");
					}
					else if (objects[i] instanceof int[])
					{
						int[] objecters = (int[]) objects[i];
						message.append("{");

						for (int j = 0; j < objecters.length; j++)
						{
							message.append(objecters[j]);

							if (j != (objecters.length - 1))
							{
								message.append(" ");
							}
						}

						message.append("}");
					}
					else if (objects[i] instanceof long[])
					{
						long[] objecters = (long[]) objects[i];
						message.append("{");

						for (int j = 0; j < objecters.length; j++)
						{
							message.append(objecters[j]);

							if (j != (objecters.length - 1))
							{
								message.append(" ");
							}
						}

						message.append("}");
					}
					else if (objects[i] instanceof float[])
					{
						float[] objecters = (float[]) objects[i];
						message.append("{");

						for (int j = 0; j < objecters.length; j++)
						{
							message.append(objecters[j]);

							if (j != (objecters.length - 1))
							{
								message.append(" ");
							}
						}

						message.append("}");
					}
					else if (objects[i] instanceof double[])
					{
						double[] objecters = (double[]) objects[i];
						message.append("{");

						for (int j = 0; j < objecters.length; j++)
						{
							message.append(objecters[j]);

							if (j != (objecters.length - 1))
							{
								message.append(" ");
							}
						}

						message.append("}");
					}
					else if (objects[i] instanceof Object[])
					{
						Object[] objecters = (Object[]) objects[i];
						message.append("{");

						for (int j = 0; j < objecters.length; j++)
						{
							message.append(objecters[j] != null ? objecters[j]
									: "null");

							if (j != (objecters.length - 1))
							{
								message.append(" ");
							}
						}

						message.append("}");
					}
					else if (objects[i] instanceof Set<?>)
					{
						Set<?> sets = ((Set<?>) objects[i]);
						Iterator<?> iterator = sets.iterator();
						int counter = 0;

						message.append("{");
						while (iterator.hasNext())
						{
							counter++;

							Object objecter = iterator.next();

							message.append(objecter != null ? objecter : "null");

							if (counter != sets.size())
							{
								message.append(" ");
							}
						}

						message.append("}");
					}
					else if (objects[i] instanceof List<?>)
					{
						List<?> list = (List<?>) objects[i];

						message.append("{");
						for (int j = 0; j < list.size(); j++)
						{
							message.append(list.get(j) != null ? list.get(j)
									.toString() : "null");

							if (j != (list.size() - 1))
							{
								message.append(" ");
							}
						}

						message.append("}");
					}
					else
					{
						message.append(objects[i].toString());
					}

				}
				else
				{
					message.append("null");
				}

				if (i != (objects.length - 1))
				{
					message.append(" ");
				}
			}
		}
		else
		{
			message.append("The log message is empty!");
		}

		message.append("\n").append(
				"****************************************************");

		return message;
	}

	/**
	 * The log level enumeration.
	 * 
	 * @author ifeegoo
	 * 
	 * @since 0.0.1
	 */
	private static enum Level
	{
		V, D, I, W, E, WTF
	}

	/**
	 * Log the message.
	 * 
	 * @param level
	 *            The log level.
	 * @param tag
	 *            The log tag.
	 * @param message
	 *            The log message.
	 * @param throwable
	 *            {@link Throwable};
	 * 
	 * @since 0.0.1
	 */
	private static void log(Level level, String tag, String message,
                            Throwable throwable)
	{
		switch (level)
		{
			case V:
			{
				Log.v(dealWithString(tag), dealWithString(message), throwable);
			}
			break;
			case D:
			{
				Log.d(dealWithString(tag), dealWithString(message), throwable);
			}
			break;
			case I:
			{
				Log.i(dealWithString(tag), dealWithString(message), throwable);
			}
			break;
			case E:
			{
				Log.e(dealWithString(tag), dealWithString(message), throwable);
			}
			break;
			case W:
			{

				Log.w(dealWithString(tag), dealWithString(message), throwable);
			}
			break;
			case WTF:
			{
				Log.wtf(dealWithString(tag), dealWithString(message), throwable);
			}
			break;
			default:
			break;
		}
	}

	/**
	 * Deal with the situation null or empty.
	 * 
	 * @param string
	 *            String you want to deal with.
	 * @return The String
	 * 
	 * @since 0.0.1
	 */
	private static String dealWithString(String string)
	{
		if (string == null)
		{
			string = "null";
		}
		else if (string == "")
		{
			string = "empty";
		}

		return string;
	}

	/**
	 * Build the stack trace information.
	 * 
	 * @param stackTraceElements
	 *            Stack trace elements.
	 * @return The stack trace information.
	 * 
	 * @since 0.0.1
	 */
	private static String buildStackTraceInformation(
			StackTraceElement[] stackTraceElements)
	{
		StringBuffer stackTraceInformation = new StringBuffer();

		String fileName = stackTraceElements[2].getFileName();
		String className = stackTraceElements[2].getClassName();
		String methodName = stackTraceElements[2].getMethodName();
		int lineNumber = stackTraceElements[2].getLineNumber();

		stackTraceInformation.append("[").append(fileName).append("]")
				.append("[").append(className).append("]").append("[")
				.append(methodName).append("]").append("[").append(lineNumber)
				.append("]").append("\n");

		return stackTraceInformation.toString();
	}

}