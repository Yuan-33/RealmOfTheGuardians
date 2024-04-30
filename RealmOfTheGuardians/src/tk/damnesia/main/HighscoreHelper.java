// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HighscoreHelper.java

package tk.damnesia.main;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class HighscoreHelper implements Runnable {

	public HighscoreHelper(String name, int score) {
		this.name = name;
		this.score = score;
	}

	public void run() {
		ArrayList lines = new ArrayList();
		lines.add((new StringBuilder(String.valueOf(name))).append(" - ")
				.append(score).append("<br>").toString());
		try {
			URL url = new URL(
					"ftp://a9764207:BTM736mt@damnesia.tk/public_html/highscores.txt");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			conn.connect();
			InputStream in = conn.getInputStream();
			ArrayList file = new ArrayList();
			int read;
			while ((read = in.read()) != -1)
				file.add(Character.valueOf((char) read));
			in.close();
			String line = "";
			for (int k = 0; k < file.size(); k++)
				if (((Character) file.get(k)).charValue() == '\n' && line != "") {
					lines.add(line);
					line = "";
				} else {
					line = (new StringBuilder(String.valueOf(line))).append(
							file.get(k)).toString();
				}

		} catch (IOException ioexception) {
		}
		for (int e = 0; e < lines.size(); e++) {
			int r = e;
			String l = (String) lines.get(e);
			for (int B = Integer.parseInt(((String) lines.get(e)).substring(
					((String) lines.get(e)).indexOf("-") + 2,
					((String) lines.get(e)).indexOf("<br>"))); r > 0
					&& Integer.parseInt(((String) lines.get(r - 1)).substring(
							((String) lines.get(r - 1)).indexOf("-") + 2,
							((String) lines.get(r - 1)).indexOf("<br>"))) < B; r--)
				lines.set(r, (String) lines.get(r - 1));

			lines.set(r, l);
		}

		try {
			URL url = new URL(
					"ftp://a9764207:BTM736mt@damnesia.tk/public_html/highscores.txt");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			conn.connect();
			OutputStream out = conn.getOutputStream();
			for (int k = 0; k < lines.size(); k++)
				out.write((new StringBuilder(String.valueOf((String) lines
						.get(k)))).append("\n").toString().getBytes());

			out.close();
		} catch (IOException ie) {
			System.out.println(ie);
		}
	}

	String name;
	int score;
}
