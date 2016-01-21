@echo off
SETLOCAL
set /p input="Input folder: "
set /p name="Pack name: "
java -cp runnable-texturepacker.jar com.badlogic.gdx.tools.texturepacker.TexturePacker %input% %input% %name%
pause