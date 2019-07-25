// Copyright (C) 2019 Karakun AG
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.

package net.adoptopenjdk.icedteaweb.integration.reproducers.progressclass.applications;

import javax.jnlp.DownloadServiceListener;
import javax.jnlp.FileContents;
import javax.jnlp.PersistenceService;
import javax.jnlp.ServiceManager;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;

/**
 * This class represents a basic IcedTea-Web managed application. It is intended to be launched by integration
 * tests to test the launching and application environment of IcedTea-Web (see the launch sequence description
 * in JSR-56, section 5.1 Launch Sequence for details).
 */
public class ProgressClassManagedApplication implements DownloadServiceListener {
    public static final String PROGRESS_CLASS_OUTPUT_FILE = "ProgressClassOutput.txt";
    private static String lastMessageFromDownloadServiceListener = "";

    public static void main(String[] args) throws Exception {
        writeFile(PROGRESS_CLASS_OUTPUT_FILE, writer -> writer.write(lastMessageFromDownloadServiceListener));
    }

    @Override
    public void progress(final URL url, final String version, final long readSoFar, final long total, final int overallPercent) {
        lastMessageFromDownloadServiceListener = "MyDownloadServiceListener.progress called";
    }

    @Override
    public void validating(final URL url, final String version, final long entry, final long total, final int overallPercent) {
        lastMessageFromDownloadServiceListener = "MyDownloadServiceListener.validating called";

    }

    @Override
    public void upgradingArchive(final URL url, final String version, final int patchPercent, final int overallPercent) {
        lastMessageFromDownloadServiceListener = "MyDownloadServiceListener.upgradingArchive called";
    }

    @Override
    public void downloadFailed(final URL url, final String version) {
        lastMessageFromDownloadServiceListener = "MyDownloadServiceListener.downloadFailed called";
    }

    private static void writeFile(final String fileName, final String content) throws Exception {
        writeFile(fileName, writer -> writer.write(content));
    }

    private static void writeFile(final String fileName, final ThrowingConsumer<Writer> consumer) throws Exception {
        final PersistenceService persistenceService = (PersistenceService) ServiceManager.lookup("PersistenceService");
        final String fileUrl = "http://localhost/" + fileName;
        persistenceService.create(new URL(fileUrl), Long.MAX_VALUE);
        final FileContents fileContents = persistenceService.get(new URL(fileUrl));
        try (final OutputStream outputStream = fileContents.getOutputStream(true)) {
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);

            consumer.accept(writer);
            writer.flush();
        }
    }

    private interface ThrowingConsumer<E> {
        void accept(E e) throws Exception;
    }
}
