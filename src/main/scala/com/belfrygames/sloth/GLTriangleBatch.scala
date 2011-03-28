package com.belfrygames.sloth

import org.lwjgl.util.vector.Vector3f
import org.lwjgl.util.vector.Vector2f

import java.nio.IntBuffer
import org.lwjgl.opengl.GL15._

class GLTriangleBatch extends GLBatchBase {
  val VERTEX_DATA =    0
  val NORMAL_DATA =    1
  val TEXTURE_DATA =   2
  val INDEX_DATA =     3

  override def finalize() {
	// Delete buffer objects
    glDeleteBuffers(bufferObjects)
  }
  // Use these three functions to add triangles
  def BeginMesh(nMaxVerts : Int) {
	pIndexes = Nil
    pVerts = Nil
    pNorms = Nil
    pTexCoords = Nil

	nMaxIndexes = nMaxVerts
    nNumIndexes = 0
    nNumVerts = 0

    // Allocate new blocks. In reality, the other arrays will be much shorter than the index array
//    pIndexes = new GLushort[nMaxIndexes];
//    pVerts = new M3DVector3f[nMaxIndexes];
//    pNorms = new M3DVector3f[nMaxIndexes];
//    pTexCoords = new M3DVector2f[nMaxIndexes];
  }
  
  def AddTriangle(verts : (Vector3f, Vector3f, Vector3f), vNorms : (Vector3f, Vector3f, Vector3f), vTexCoords : (Vector2f, Vector2f, Vector2f)) {

  }
  
  def End(){

  }

  // Useful for statistics
  def GetIndexCount() = nNumIndexes
  def GetVertexCount() = nNumVerts

  // Draw - make sure you call glEnableClientState for these arrays
  override def Draw() {
	
  }

  var pIndexes : List[Int] = _ // Array of indexes
  var pVerts : List[Vector3f] = _ // Array of vertices
  var pNorms : List[Vector3f] = _ // Array of normals
  var pTexCoords : List[Vector2f] = _ // Array of texture coordinates


  var nMaxIndexes : Int = 0         // Maximum workspace
  var nNumIndexes : Int = 0         // Number of indexes currently used
  var nNumVerts : Int = 0           // Number of vertices actually used

  val bufferObjects : IntBuffer = null
//  val bufferObjects = new Array[Int](0,0,0,0)
  var vertexArrayBufferObject : Int = 0
};

