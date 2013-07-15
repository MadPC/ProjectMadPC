#version 330

in vec4 inColor;

out vec4 outputColor;

void main()
{
	float xValue = gl_FragCoord.x / 800.0f;
	float yValue = gl_FragCoord.y / 600.0f;
    
    outputColor = vec4(mix(1.0f, 0.0f, xValue), mix(1.0f, 0.0f, yValue), 0.5f, 1.0f);
}