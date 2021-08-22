
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONException;
import org.json.JSONObject;

public class Main {

/*	static final String ABOUT_INFO = "#######感谢@Lin#######"
	+ "\n#######感谢@千城#######"
	+ "\n#######本api实时对接官方接口#######"
	+ "\n#######请不要在其他地方发布本api#######"
	+ "\n#######by_昊天#######";*/

	static final File STORAGE_DIRECTORY = new File("/www/wwwroot/flashproxy.cn/WG");

	//这里填手机号码
	private static String phoneNumber = "13152028025";

	public static String md5(String string) {
        try {
			return new BigInteger(1, MessageDigest.getInstance("MD5").digest(string.getBytes(StandardCharsets.UTF_8))).toString(16);
        } catch (NoSuchAlgorithmException ex) {
			return "";
        }
    }
	public static void main(String[] args) throws Throwable {

		//加密手机号码
		String phone = encryptPhone(phoneNumber);
		phone = URLEncoder.encode(phone, "UTF-8");
		String data = null; 
		String result = null;{
			JSONObject sendJson = new JSONObject();
			sendJson.put("data", phone);
			result = httpPostWithjson(sendJson.toString());
			JSONObject jo = new JSONObject(result);
			if (jo.has("retCode") && "0".equals(jo.getString("retCode"))) {
				if (jo.has("data")) {
					data = jo.getString("data");
				}
			}
		}
		if (data != null) {

			System.out.println(result);

			//处理url编码
			String decode = URLDecoder.decode(data, "UTF-8");

			Decryptor decryptor = new Decryptor();
			String enString = decryptor.decrypt(decode);
			System.out.println(enString);

		
		    writeFile(new File(STORAGE_DIRECTORY, "WG.php"), parseData(enString) + "\n\n" );
			writeFile(new File(STORAGE_DIRECTORY, "1.txt"),  enString);

		/*	System.out.println(ABOUT_INFO);*/
		} else {
			System.out.println(result);
		}


    }

	//加密手机号
	private static String encryptPhone(String phone) {

		SecretKeySpec secretKeySpec = new SecretKeySpec("dca67a249fdd1dcf29085e4d51c1365f".substring(0, 24).getBytes(StandardCharsets.UTF_8), "DESede");
        IvParameterSpec ivParameterSpec = new IvParameterSpec("anwxpore".getBytes());

		String json = "{\"mobile\":\"" + phone + "\",\"packageId\":\"com.sinovatech.unicom.ui\"}";

		try {
            Cipher instance = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            instance.init(1, secretKeySpec, ivParameterSpec);
			return Base64.encodeToString(instance.doFinal(json.getBytes()), 2).replaceAll("[\\n\\r]", "");
        } catch (Exception e2) {
			e2.printStackTrace();
			return "";
        }

	}

	//解密手机号
	private static String decryptPhone(String phone) {

		SecretKeySpec secretKeySpec = new SecretKeySpec("dca67a249fdd1dcf29085e4d51c1365f".substring(0, 24).getBytes(StandardCharsets.UTF_8), "DESede");
        IvParameterSpec ivParameterSpec = new IvParameterSpec("anwxpore".getBytes());

		try {
            Cipher instance = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            instance.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
			return new String(instance.doFinal(Base64.decode(phone, 2)), "UTF-8");
        } catch (Exception e2) {
			return e2.toString();
        }

	}

	private static void writeFile(File file, String data) {
		try {
			file.getParentFile().mkdirs();
			FileWriter fw = new FileWriter(file);
			fw.write(data);
			fw.close();
		} catch (Throwable e) {
			System.out.println(e.toString());
		}
	}

	private static String parseData(String data) throws JSONException {
		JSONObject jo = new JSONObject(data);
		StringBuffer sb = new StringBuffer();
		sb.append("[Interface]");
		sb.append("\nAddress = ").append(jo.getString("clientIp"));
		sb.append("\nDNS = ").append(jo.getString("clientDns"));
		sb.append("\nPrivateKey = ").append(jo.getString("clientKey"));
		sb.append("\n\n[Peer]");
		sb.append("\nAllowedIPs = ").append("0.0.0.0/0");
		sb.append("\nEndpoint = ").append(jo.getString("serverIp"));
		sb.append("\nPublicKey = ").append(jo.getString("serverKey"));
		return sb.toString();
	}

	public static String httpPostWithjson(String json) throws Exception {
		String resp= null;
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL("https://oms.flow.wostore.cn/server/achieve/shouting/config/api/v1").openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type", "application/json");

			{
				String format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'", Locale.CHINA).format(new Date());
				String passwordDigest = Base64.encodeToString(
					MessageDigest.getInstance("SHA-256")
					.digest(("c22edd3b6a33af309e4c4667a53b4364".toUpperCase(Locale.ROOT) 
							+ format
							+ "xwo(2021)StWGuard***Secret").getBytes()), 2);

				String nonce = md5("xwoStWGSdk2021").toUpperCase(Locale.ROOT);
				String timestamp = format.toString();

				connection.setRequestProperty("X-WSSE", "UsernameToken Username=\"N/A\",PasswordDigest=\"" + passwordDigest + "\",SdkName=wgSdk,Nonce=\"" + nonce + "\",Timestamp=\"" + timestamp + "\""); 
			}

			connection.connect();

			try  {
				OutputStream os = connection.getOutputStream();
				os.write(json.getBytes("UTF-8"));
			} catch (Throwable e) {}

			try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(connection.getInputStream()))) {
				String lines;
				StringBuffer sbf = new StringBuffer();
				while ((lines = reader.readLine()) != null) {
					lines = new String(lines.getBytes(), "utf-8");
					sbf.append(lines);
				}
				resp = sbf.toString();
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return resp;
	}

	public static class Decryptor {
		public static final String TAG = "Decrypt";

		public final SecretKey getSecretKeySpec() {
			try {
				return new SecretKeySpec(new BigInteger(1, MessageDigest.getInstance("MD5").digest("unicomWGSecret@2021".getBytes())).toString(16).substring(0, 24).getBytes(), "DESede");
			} catch (Exception unused) {
				return null;
			}
		}

		public String decrypt(String str) {
			byte[] decode = Base64.decode(str, 2);
			byte[] bArr = new byte[0];
			try {
				SecretKey b = getSecretKeySpec();
				byte[] bArr2 = new byte[8];
				for (int i = 0; i < 8; i++) {
					bArr2[i] = 48;
				}
				IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr2);
				Cipher instance = Cipher.getInstance("DESede/CBC/NoPadding");
				instance.init(2, b, ivParameterSpec);
				bArr = instance.doFinal(decode);
			} catch (Exception e) {
				System.out.println(e);
			}
			return new String(bArr, StandardCharsets.UTF_8);
		}
	}


}
