#version 330

out vec3 pixelFarbe;
in vec3 normale;
in vec3 p;
vec3 lichtposition = vec3(10.0f, 10.0f, 0.0f);

in vec2 uv;
uniform sampler2D s;

void main(){

    /*vec3 N = normalize(normale);
    vec3 L = normalize(lichtposition - p);
    vec3 R = reflect(-L,N);
    vec3 V = normalize(-p);

    float I = 128 + 1 * (dot(L,N)+ pow( dot(R,V),1 ));*/

    vec3 lichtpixel = normalize(lichtposition-p);
    float il = 1;
    float ia = 0;
    float id = max(0,dot(lichtpixel,normale))*il;
    vec3 r = normalize(2*(dot(lichtpixel,normale))*normale-lichtpixel);
    vec3 v = normalize(-p);
    float is = pow(max(0,dot(r,v)),100)*il;
    float i = ia+id+is;

    pixelFarbe = i * texture(s,uv).rgb; //vereinfacht
}