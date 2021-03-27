#version 330

out vec3 pixelFarbe;
//in vec3 farbUebertragung;
in float ww;
in vec2 uv;
uniform sampler2D s;
void main(){

    pixelFarbe = texture(s, vec2(uv.x,uv.y+(ww*0.05))).rgb;
    //pixelFarbe = farbUebertragung;
}