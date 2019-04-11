@echo off

REM panel

aseprite -b ui-ase/panel.9.aseprite --palette palette-primary.pal --save-as ui/panel-primary.9.png
aseprite -b ui-ase/panel.9.aseprite --palette palette-secondary.pal --save-as ui/panel-secondary.9.png

REM window

aseprite -b ui-ase/window.9.aseprite --palette palette-primary.pal --save-as ui/window-primary.9.png
aseprite -b ui-ase/window.9.aseprite --palette palette-secondary.pal --save-as ui/window-secondary.9.png

aseprite -b ui-ase/window-large.9.aseprite --palette palette-primary.pal --save-as ui/window-large-primary.9.png
aseprite -b ui-ase/window-large.9.aseprite --palette palette-secondary.pal --save-as ui/window-large-secondary.9.png

REM button

aseprite -b ui-ase/button.9.aseprite --layer up --palette palette-primary.pal --save-as ui/button-primary.9.png
aseprite -b ui-ase/button.9.aseprite --layer down --palette palette-primary.pal --save-as ui/button-primary-down.9.png

aseprite -b ui-ase/button.9.aseprite --layer up --palette palette-secondary.pal --save-as ui/button-secondary.9.png
aseprite -b ui-ase/button.9.aseprite --layer down --palette palette-secondary.pal --save-as ui/button-secondary-down.9.png