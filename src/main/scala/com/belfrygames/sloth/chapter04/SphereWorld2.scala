package com.belfrygames.sloth.chapter04

import com.belfrygames.sloth.Math3D._
import com.belfrygames.sloth._
import com.belfrygames.sloth.GLShaderManager._
import com.belfrygames.sloth.GLTools._
import com.belfrygames.sloth.glut._
import com.belfrygames.sloth.glut.Internal._

import org.lwjgl.opengl.GL11._

object SphereWorld2 {
  val shaderManager = GLShaderManager
  val modelViewMatrix = new GLMatrixStack
  val projectionMatrix = new GLMatrixStack
  val viewFrustum = new GLFrustum
  val transformPipeline = new GLGeometryTransform

  val torusBatch = new GLTriangleBatch
  val floorBatch = new GLBatch

  val sphereBatch = new GLTriangleBatch
  val cameraFrame = new GLFrame

  //////////////////////////////////////////////////////////////////
  // This function does any needed initialization on the rendering
  // context.
  def SetupRC() {
	// Initialze Shader Manager
	shaderManager.InitializeStockShaders();

	glEnable(GL_DEPTH_TEST);
	glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

	glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

	// This makes a torus
	gltMakeTorus(torusBatch, 0.4f, 0.15f, 30, 30);

    // This make a sphere
    gltMakeSphere(sphereBatch, 0.1f, 26, 13);

	floorBatch.Begin(GL_LINES, 324);
	var x = -20.0f
    while(x <= 20.0f) {
	  floorBatch.Vertex3f(x, -0.55f, 20.0f);
	  floorBatch.Vertex3f(x, -0.55f, -20.0f);

	  floorBatch.Vertex3f(20.0f, -0.55f, x);
	  floorBatch.Vertex3f(-20.0f, -0.55f, x);
	  x += 0.5f
	}
    floorBatch.End();
  }


  ///////////////////////////////////////////////////
  // Screen changes size or is initialized
  def ChangeSize(nWidth : Int, nHeight : Int) {
	glViewport(0, 0, nWidth, nHeight);

    // Create the projection matrix, and load it on the projection matrix stack
	viewFrustum.SetPerspective(35.0f, nWidth.toFloat/ nHeight.toFloat, 1.0f, 100.0f);
	projectionMatrix.LoadMatrix(viewFrustum.GetProjectionMatrix());

    // Set the transformation pipeline to use the two matrix stacks
	transformPipeline.SetMatrixStacks(modelViewMatrix, projectionMatrix);
  }


  // Respond to arrow keys by moving the camera frame of reference
  def SpecialKeys(key : Int, x : Int, y : Int)
  {
	val linear = 0.1f;
	val angular = m3dDegToRad(5.0f).toFloat

	if(key == GLUT_KEY_UP)
	  cameraFrame.MoveForward(linear);

	if(key == GLUT_KEY_DOWN)
	  cameraFrame.MoveForward(-linear);

	if(key == GLUT_KEY_LEFT)
	  cameraFrame.RotateWorld(angular, 0.0f, 1.0f, 0.0f);

	if(key == GLUT_KEY_RIGHT)
	  cameraFrame.RotateWorld(-angular, 0.0f, 1.0f, 0.0f);
  }

  val vFloorColor = M3DVector(0.0f, 1.0f, 0.0f, 1.0f);
  val vTorusColor = M3DVector(1.0f, 0.0f, 0.0f, 1.0f);
  val vSphereColor = M3DVector(0.0f, 0.0f, 1.0f, 1.0f);
  lazy val rotTimer = new CStopWatch
// Called to draw scene
  def RenderScene() {
    // Color values

    // Time Based animation
	val yRot = rotTimer.GetElapsedSeconds() * 60.0f;

	// Clear the color and depth buffers
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);


    // Save the current modelview matrix (the identity matrix)
	modelViewMatrix.PushMatrix();

    val mCamera = new M3DMatrix44f
    cameraFrame.GetCameraMatrix(mCamera);
    modelViewMatrix.PushMatrix(mCamera);

	// Draw the ground
	shaderManager.UseStockShader(GLT_SHADER_FLAT,
								 transformPipeline.GetModelViewProjectionMatrix(),
								 vFloorColor);
	floorBatch.Draw();

    // Draw the spinning Torus
    modelViewMatrix.Translate(0.0f, 0.0f, -2.5f);

    // Save the Translation
    modelViewMatrix.PushMatrix();

	// Apply a rotation and draw the torus
	modelViewMatrix.Rotate(yRot, 0.0f, 1.0f, 0.0f);
	shaderManager.UseStockShader(GLT_SHADER_FLAT, transformPipeline.GetModelViewProjectionMatrix(),
								 vTorusColor);
	torusBatch.Draw();
    modelViewMatrix.PopMatrix(); // "Erase" the Rotation from before

    // Apply another rotation, followed by a translation, then draw the sphere
    modelViewMatrix.Rotate(yRot * -2.0f, 0.0f, 1.0f, 0.0f);
    modelViewMatrix.Translate(0.8f, 0.0f, 0.0f);
    shaderManager.UseStockShader(GLT_SHADER_FLAT, transformPipeline.GetModelViewProjectionMatrix(),
								 vSphereColor);
    sphereBatch.Draw();

	// Restore the previous modleview matrix (the identity matrix)
	modelViewMatrix.PopMatrix();
    modelViewMatrix.PopMatrix();
    // Do the buffer Swap
    glutSwapBuffers();

    // Tell GLUT to do it again
    glutPostRedisplay();
  }

  def main(args: Array[String]): Unit = {
	if (args.size > 0) gltSetWorkingDirectory(args(0))

    glutInit(args);
    glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);
    glutInitWindowSize(800,600);

    glutCreateWindow("OpenGL SphereWorld");

    glutSpecialFunc(SpecialKeys);
    glutReshapeFunc(ChangeSize);
    glutDisplayFunc(RenderScene);

//    GLenum err = glewInit();
//    if (GLEW_OK != err) {
//	  fprintf(stderr, "GLEW Error: %s\n", glewGetErrorString(err));
//	  return 1;
//	}

    SetupRC();
    glutMainLoop();
    return 0;
  }
}