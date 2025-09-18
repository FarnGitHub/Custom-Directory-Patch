package farn.customDirectory;

import net.lenni0451.classtransform.InjectionCallback;
import net.lenni0451.classtransform.annotations.CTarget;
import net.lenni0451.classtransform.annotations.CTransformer;
import net.lenni0451.classtransform.annotations.injection.CInject;
import net.minecraft.client.Minecraft;

import java.io.File;

@CTransformer(Minecraft.class)
public class TransformMinecraft {

    @CInject(method="getAppDir", target = @CTarget("HEAD"), cancellable = true)
    public static void redirectAppDirectory(String pathName, InjectionCallback callback) {
        if (pathName.startsWith("minecraft/")) {
            String relative = pathName.substring("minecraft/".length());
            File baseDir = new File(System.getProperty("user.dir"));
            File targetDir = relative.isEmpty() ? baseDir : new File(baseDir, relative);
            targetDir.mkdirs();
            callback.setReturnValue(targetDir);
        } else if(pathName.equals("minecraft")) {
            callback.setReturnValue(new File(System.getProperty("user.dir")));
        }
    }
}
