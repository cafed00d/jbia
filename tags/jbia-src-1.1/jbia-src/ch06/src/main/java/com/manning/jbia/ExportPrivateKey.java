/*
 * This code was borrowed from the following JBoss website:
 * http://wiki.jboss.org/wiki/Wiki.jsp?page=SSLSetup
 */

package com.manning.jbia;

import java.io.File;
import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

import sun.misc.BASE64Encoder;

public class ExportPrivateKey {
	public static void main(String args[]) throws Exception {
		if (args.length < 2) {
			System.err
					.println("Usage: java ExportPriv <keystore> <alias> <password>");
			System.exit(1);
		}
		ExportPrivateKey myep = new ExportPrivateKey();
		myep.doit(args[0], args[1], args[2]);
	}

	public void doit(String fileName, String aliasName, String pass)
			throws Exception {

		KeyStore ks = KeyStore.getInstance("JKS");

		char[] passPhrase = pass.toCharArray();
		BASE64Encoder myB64 = new BASE64Encoder();

		File certificateFile = new File(fileName);
		ks.load(new FileInputStream(certificateFile), passPhrase);

		KeyPair kp = getPrivateKey(ks, aliasName, passPhrase);

		PrivateKey privKey = kp.getPrivate();

		String b64 = myB64.encode(privKey.getEncoded());

		System.out.println("-----BEGIN PRIVATE KEY-----");
		System.out.println(b64);
		System.out.println("-----END PRIVATE KEY-----");

	}

	// From http://javaalmanac.com/egs/java.security/GetKeyFromKs.html

	public KeyPair getPrivateKey(KeyStore keystore, String alias,
			char[] password) throws Exception {
		// Get private key
		Key key = keystore.getKey(alias, password);
		if (key instanceof PrivateKey) {
			// Get certificate of public key
			Certificate cert = keystore.getCertificate(alias);

			// Get public key
			PublicKey publicKey = cert.getPublicKey();

			// Return a key pair
			return new KeyPair(publicKey, (PrivateKey) key);
		}
		return null;
	}

}
