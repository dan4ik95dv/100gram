package me.stogram.android.util;

import android.content.Context;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;

/**
 * Created by Daniil Celikin on 17.02.15.
 * origin kinovlru
 */
public class Utils {


	private static final String TAG = Utils.class.getName();

	public static Object loadObject(Context context, String fileName) {
		File file = new File(context.getCacheDir(), fileName);
		return loadObject(file);
	}
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			if (listItem instanceof ViewGroup) {
				listItem.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT));
			}
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() ));
		listView.setLayoutParams(params);
	}
	public static <T> T loadObject(File file) {
		Object instance = null;
		if (file.exists()) {
			ObjectInputStream ois = null;
			try {
				try {
					ois = new ObjectInputStream(new FileInputStream(file));
					instance = ois.readObject();
				} finally {
					if (ois != null)
						ois.close();
				}
			} catch (Exception e) {
				Log.e(TAG, "loadObject from " + file + " failed", e);
			}
		}
		try {
			return (T) instance;
		} catch (ClassCastException e) {
			return null;
		}
	}

	public static void saveObject(Context context, String fileName, Object data) {
		saveObject(new File(context.getCacheDir(), fileName), data);
	}

	public static void saveObject(File file, Object data) {
		ObjectOutputStream oos = null;
		try {
			try {
				oos = new ObjectOutputStream(new FileOutputStream(file));
				oos.writeObject(data);
			} finally {
				if (oos != null)
					oos.close();
			}
		} catch (Exception e) {
			Log.e(TAG, "saveObject to " + file + " failed", e);
		}
	}

	public static void deleteFile(Context context, String fileName) {
		new File(context.getCacheDir(), fileName).delete();
	}

	public static String readStringFromStream(InputStream is) throws IOException {
		StringBuilder sb = new StringBuilder();
		if (is != null) {
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));

				String line;
				while ((line = reader.readLine()) != null) {
					sb.append(line).append('\n');
				}
			} finally {
				is.close();
			}
		}

		return sb.toString();
	}


	public static void copyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (; ; ) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception e) {
			Log.e(TAG, "copyStream", e);
		}
	}

	public static void createDir(final File dir) throws IOException {
		if (!dir.exists())
			if (!dir.mkdirs())
				throw new IOException("Making dirs failed: " + dir.getAbsolutePath());
	}

	public static void deleteDir(File fileOrDirectory) {
		if (fileOrDirectory.isDirectory())
			for (File child : fileOrDirectory.listFiles())
				deleteDir(child);

		fileOrDirectory.delete();
	}

	public static String md5(final String s) {
		final String MD5 = "MD5";
		try {
			// Create MD5 Hash
			MessageDigest digest = MessageDigest
				.getInstance(MD5);
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();

			// Create Hex String
			StringBuilder hexString = new StringBuilder();
			for (byte aMessageDigest : messageDigest) {
				String h = Integer.toHexString(0xFF & aMessageDigest);
				while (h.length() < 2)
					h = "0" + h;
				hexString.append(h);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String encrypt(String clearText, byte[] keyBytes)
		throws NoSuchPaddingException, NoSuchAlgorithmException,
		InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, keySpec);
		byte[] cipherText = cipher.doFinal(clearText.getBytes());
		return Base64.encodeToString(cipherText, Base64.DEFAULT);
	}

	public static String decrypt(String cipherText, byte[] keyBytes) throws NoSuchPaddingException,
		NoSuchAlgorithmException,
		InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, keySpec);
		byte[] clearText = cipher.doFinal(Base64.decode(cipherText.getBytes(), Base64.DEFAULT));

		return new String(clearText);
	}

	public static void closeQuietly(Closeable closeable) {
		if (closeable != null)
			try {
				closeable.close();
			} catch (IOException e) {
				Log.e(TAG, "Unable to close quietly", e);
			}
	}

	public static void deleteQuietly(final File file) {
		if (file != null && file.exists()) {
			if (file.isDirectory())
				for (final File f : file.listFiles())
					deleteQuietly(f);

			if (!file.delete())
				file.deleteOnExit();
		}
	}

	public static void deleteSurely(final File file) throws IOException {
		if (file.isDirectory())
			for (final File f : file.listFiles())
				deleteSurely(f);

		if (!file.delete())
			throw new IOException("File " + file.getPath() + " failed");
	}

	public static boolean isUiThread() {
		return Looper.getMainLooper() == Looper.myLooper();
	}

	public static void assertUiThread() {
		if (!isUiThread())
			throw new RuntimeException("It should be UI thread");
	}

	public static void assertBackgroundThread() {
		if (isUiThread())
			throw new RuntimeException("It should be background thread");
	}

	public static String packArrayWithDelimiter(String[] array, String delimiter, String lastDelimiter) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			builder.append(array[i]);
			if (i != array.length - 1) {
				builder.append(delimiter);
			} else {
				builder.append(lastDelimiter);
			}
		}
		return builder.toString();
	}

	public static String packArrayWithDelimiter(long[] array, String delimiter, String lastDelimiter) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			builder.append(array[i]);
			if (i != array.length - 1) {
				builder.append(delimiter);
			} else {
				builder.append(lastDelimiter);
			}
		}
		return builder.toString();
	}

	public static String packArrayWithDelimiter(int[] array, String delimiter, String lastDelimiter) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			builder.append(array[i]);
			if (i != array.length - 1) {
				builder.append(delimiter);
			} else {
				builder.append(lastDelimiter);
			}
		}
		return builder.toString();
	}

	public static String packArrayWithDelimiter(List<Integer> array, String delimiter, String lastDelimiter) {
		StringBuilder builder = new StringBuilder();
		int size = array.size();
		for (int i = 0; i < size; i++) {
			builder.append(array.get(i));
			if (i != size - 1) {
				builder.append(delimiter);
			} else {
				builder.append(lastDelimiter);
			}
		}
		return builder.toString();
	}

	public static int daysBetween(long from, long to) {
		return (int) ((to - from) / (1000 * 60 * 60 * 24));
	}


	public static String dateTime2String(long dateTime) {
		int minutes = (int) ((System.currentTimeMillis() - dateTime * 1000) / 60000);
		return "обновлено " + ((minutes >= 60) ? Math.round(minutes / 60) + " ч." : minutes + " мин.") + " назад";
	}

	public static long randInt(int min, int max) {
		Random rand = new Random();
		return (long) (rand.nextInt((max - min) + 1) + min);
	}

	public static double y2lat(double aY) {
		return Math.toDegrees(2 * Math.atan(Math.exp(Math.toRadians(aY))) - Math.PI / 2);
	}

	public static double lat2y(double aLat) {
		return Math.toDegrees(Math.log(Math.tan(Math.PI / 4 + Math.toRadians(aLat) / 2)));
	}


	public static String randomString(int len) {
		String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}


	public static String sha256(String base) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(base.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();

			for (byte aHash : hash) {
				String hex = Integer.toHexString(0xff & aHash);
				if (hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}

			return hexString.toString();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}
