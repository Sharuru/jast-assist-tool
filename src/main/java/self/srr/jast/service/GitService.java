package self.srr.jast.service;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * Git service
 * <p>
 * Created by Sharuru on 2017/06/30.
 */
@Service
@Slf4j
public class GitService {


    public Object clone() {
        String url = "https://github.com/Sharuru/jast-file-tracer.git";

        File localPath = null;
        try {
            localPath = File.createTempFile("d:\\TempRepo","");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!localPath.delete()){
            System.out.println("ERROR");
        }
        try{
            Git result = Git.cloneRepository()
                    .setURI(url)
                    .setDirectory(localPath)
                    .call();
            result.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
