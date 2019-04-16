@echo off

REM basic color drawable

aseprite -b ui-ase/color.9.aseprite --palette palette-primary.pal --save-as ui/primary.9.png
aseprite -b ui-ase/color.9.aseprite --palette palette-secondary.pal --save-as ui/secondary.9.png

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

aseprite -b ui-ase/button.9.aseprite --layer up --palette palette-good.pal --save-as ui/button-good.9.png
aseprite -b ui-ase/button.9.aseprite --layer down --palette palette-good.pal --save-as ui/button-good-down.9.png

REM progress bar

aseprite -b ui-ase/progress-bar.9.aseprite --layer background --palette palette-primary.pal --save-as ui/progress-bar-background.9.png
aseprite -b ui-ase/progress-bar.9.aseprite --layer knobBefore --palette palette-primary.pal --save-as ui/progress-bar-knobBefore.9.png

REM slider

aseprite -b ui-ase/slider.9.aseprite --layer background --palette palette-primary.pal --save-as ui/slider-background.9.png
aseprite -b ui-ase/slider.9.aseprite --layer knobBefore --palette palette-primary.pal --save-as ui/slider-knobBefore.9.png
aseprite -b ui-ase/slider.9.aseprite --layer knob --palette palette-primary.pal --save-as ui/slider-knob.9.png
