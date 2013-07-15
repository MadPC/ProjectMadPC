#version 330

layout(location = 0) in vec4 position;

uniform mat4 offset;
uniform mat4 perspectiveMatrix;

out vec4 inColor;

void main()
{
	vec4 cameraPos = position * offset;
    
    gl_Position = perspectiveMatrix * cameraPos;
    inColor = vec4(1.0f, 1.0f, 0.5f, 1.0f);
}