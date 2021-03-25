package sample;

import java.io.IOException;
import java.nio.file.*;

public class WatchDirectory {

    WatchService watchService;
    Path path;
    WatchKey key;
    private Main app;

    public WatchDirectory(Main app) throws IOException {

        this.watchService = FileSystems.getDefault().newWatchService();
        path = Paths.get("F:/Models/Consensus(3)/images");
        path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
        this.app = app;
    }

    public void register() {
        try {
            for (; ; ) {

                this.key = watchService.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind kind = event.kind();
                    if (kind.toString().equals("ENTRY_MODIFY")) {
                        app.notifyNeedUpdate();
                    }

                    boolean valid = key.reset();

                    if (!valid) {
                        break;
                    }
                }
            }
        }
        catch (InterruptedException e) {

        }
    }
}