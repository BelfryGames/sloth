package com.belfrygames.sloth

import java.awt.BorderLayout
import java.awt.Canvas
import java.awt.Dimension
import java.awt.event.ComponentEvent
import java.awt.event.ComponentListener
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent

import javax.swing.JFrame
import org.lwjgl.input.Keyboard
import org.lwjgl.input.Mouse
import org.lwjgl.opengl.Display
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.PixelFormat


class VarArgs[T](args: Seq[T], var position: Int = 0) {
  def arg[A]: A = {
    val ret = args(position).asInstanceOf[A]
    position += 1
    ret
  }
}

package object glut {

  import com.belfrygames.sloth.glut.GLUT_EXT._
  import com.belfrygames.sloth.glut.Internal._
  import com.belfrygames.sloth.glut.Internal.fgExecutionState._

  /*
   * GLUT API macro definitions -- the special key codes:
   */
  val GLUT_KEY_F1 = 0x0001
  val GLUT_KEY_F2 = 0x0002
  val GLUT_KEY_F3 = 0x0003
  val GLUT_KEY_F4 = 0x0004
  val GLUT_KEY_F5 = 0x0005
  val GLUT_KEY_F6 = 0x0006
  val GLUT_KEY_F7 = 0x0007
  val GLUT_KEY_F8 = 0x0008
  val GLUT_KEY_F9 = 0x0009
  val GLUT_KEY_F10 = 0x000A
  val GLUT_KEY_F11 = 0x000B
  val GLUT_KEY_F12 = 0x000C
  val GLUT_KEY_LEFT = 0x0064
  val GLUT_KEY_UP = 0x0065
  val GLUT_KEY_RIGHT = 0x0066
  val GLUT_KEY_DOWN = 0x0067
  val GLUT_KEY_PAGE_UP = 0x0068
  val GLUT_KEY_PAGE_DOWN = 0x0069
  val GLUT_KEY_HOME = 0x006A
  val GLUT_KEY_END = 0x006B
  val GLUT_KEY_INSERT = 0x006C

  val INVALID_MODIFIERS = 0xffffffff

  /*
   * GLUT API macro definitions -- the display mode definitions
   */
  val GLUT_RGB = 0x0000
  val GLUT_RGBA = 0x0000
  val GLUT_INDEX = 0x0001
  val GLUT_SINGLE = 0x0000
  val GLUT_DOUBLE = 0x0002
  val GLUT_ACCUM = 0x0004
  val GLUT_ALPHA = 0x0008
  val GLUT_DEPTH = 0x0010
  val GLUT_STENCIL = 0x0020
  val GLUT_MULTISAMPLE = 0x0080
  val GLUT_STEREO = 0x0100
  val GLUT_LUMINANCE = 0x0200

  /*
   * GLUT API macro definitions -- additional keyboard and joystick definitions
   */
  val GLUT_KEY_REPEAT_OFF = 0x0000
  val GLUT_KEY_REPEAT_ON = 0x0001
  val GLUT_KEY_REPEAT_DEFAULT = 0x0002

  val fgState = new SFG_State()

  def fgError(fmt: String) {
    println("Sloth GLUT")
    println(fgState.ProgramName)
    String.format(fmt)
  }

  def fgError(fmt: String, message: String*) {
    println("Sloth GLUT")
    println(fgState.ProgramName)
    String.format(fmt, message)
  }

  def fgSystemTime() = System.nanoTime

  def fgCreateStructure() {
    /*
     * We will be needing two lists: the first containing windows,
     * and the second containing the user-defined menus.
     * Also, no current window/menu is set, as none has been created yet.
     */

    //    fgListInit(&fgStructure.Windows);
    //    fgListInit(&fgStructure.Menus);
    //    fgListInit(&fgStructure.WindowsToDestroy);
    //
    //    fgStructure.CurrentWindow = NULL;
    //    fgStructure.CurrentMenu = NULL;
    //    fgStructure.MenuContext = NULL;
    //    fgStructure.GameModeWindow = NULL;
    //    fgStructure.WindowID = 0;
    //    fgStructure.MenuID = 0;
  }

  def glutInit(args: Array[String]) {
    val displayName = ""
    val geometry = ""

    if (fgState.Initialised)
      fgError("illegal glutInit() reinitialization attempt")

    if (args.size > 0) {
      fgState.ProgramName = args(0)
    }

    Keyboard.enableRepeatEvents(true)

    fgCreateStructure();

    /* Get start time */
    fgState.Time = fgSystemTime();
  }

  //	  {
  //		  char* displayName = NULL;
  //		  char* geometry = NULL;
  //		  int i, j, argc = *pargc;


  //
  //		  /* check if GLUT_FPS env var is set */
  //	  #ifndef _WIN32_WCE
  //		  {
  //		  /* will return true for VC8 (VC2005) and higher */
  //	  #if TARGET_HOST_MS_WINDOWS && ( _MSC_VER >= 1400 ) && HAVE_ERRNO
  //			  char* fps = NULL;
  //			  err = _dupenv_s( &fps, &sLen, "GLUT_FPS" );
  //			  if (err)
  //				  fgError("Error getting GLUT_FPS environment variable");
  //	  #else
  //			  const char *fps = getenv( "GLUT_FPS" );
  //	  #endif
  //			  if( fps )
  //			  {
  //				  int interval;
  //				  sscanf( fps, "%d", &interval );
  //
  //				  if( interval <= 0 )
  //					  fgState.FPSInterval = 5000;  /* 5000 millisecond default */
  //				  else
  //					  fgState.FPSInterval = interval;
  //			  }
  //		  /* will return true for VC8 (VC2005) and higher */
  //	  #if TARGET_HOST_MS_WINDOWS && ( _MSC_VER >= 1400 ) && HAVE_ERRNO
  //			  free ( fps );  fps = NULL;  /* dupenv_s allocates a string that we must free */
  //	  #endif
  //		  }
  //
  //		  /* will return true for VC8 (VC2005) and higher */
  //	  #if TARGET_HOST_MS_WINDOWS && ( _MSC_VER >= 1400 ) && HAVE_ERRNO
  //		  err = _dupenv_s( &displayName, &sLen, "DISPLAY" );
  //		  if (err)
  //			  fgError("Error getting DISPLAY environment variable");
  //	  #else
  //		  displayName = getenv( "DISPLAY" );
  //	  #endif
  //
  //		  for( i = 1; i < argc; i++ )
  //		  {
  //			  if( strcmp( argv[ i ], "-display" ) == 0 )
  //			  {
  //				  if( ++i >= argc )
  //					  fgError( "-display parameter must be followed by display name" );
  //
  //				  displayName = argv[ i ];
  //
  //				  argv[ i - 1 ] = NULL;
  //				  argv[ i     ] = NULL;
  //				  ( *pargc ) -= 2;
  //			  }
  //			  else if( strcmp( argv[ i ], "-geometry" ) == 0 )
  //			  {
  //				  if( ++i >= argc )
  //					  fgError( "-geometry parameter must be followed by window "
  //							   "geometry settings" );
  //
  //				  geometry = argv[ i ];
  //
  //				  argv[ i - 1 ] = NULL;
  //				  argv[ i     ] = NULL;
  //				  ( *pargc ) -= 2;
  //			  }
  //			  else if( strcmp( argv[ i ], "-direct" ) == 0)
  //			  {
  //				  if( fgState.DirectContext == GLUT_FORCE_INDIRECT_CONTEXT )
  //					  fgError( "parameters ambiguity, -direct and -indirect "
  //						  "cannot be both specified" );
  //
  //				  fgState.DirectContext = GLUT_FORCE_DIRECT_CONTEXT;
  //				  argv[ i ] = NULL;
  //				  ( *pargc )--;
  //			  }
  //			  else if( strcmp( argv[ i ], "-indirect" ) == 0 )
  //			  {
  //				  if( fgState.DirectContext == GLUT_FORCE_DIRECT_CONTEXT )
  //					  fgError( "parameters ambiguity, -direct and -indirect "
  //						  "cannot be both specified" );
  //
  //				  fgState.DirectContext = GLUT_FORCE_INDIRECT_CONTEXT;
  //				  argv[ i ] = NULL;
  //				  (*pargc)--;
  //			  }
  //			  else if( strcmp( argv[ i ], "-iconic" ) == 0 )
  //			  {
  //				  fgState.ForceIconic = GL_TRUE;
  //				  argv[ i ] = NULL;
  //				  ( *pargc )--;
  //			  }
  //			  else if( strcmp( argv[ i ], "-gldebug" ) == 0 )
  //			  {
  //				  fgState.GLDebugSwitch = GL_TRUE;
  //				  argv[ i ] = NULL;
  //				  ( *pargc )--;
  //			  }
  //			  else if( strcmp( argv[ i ], "-sync" ) == 0 )
  //			  {
  //				  fgState.XSyncSwitch = GL_TRUE;
  //				  argv[ i ] = NULL;
  //				  ( *pargc )--;
  //			  }
  //		  }
  //
  //		  /* Compact {argv}. */
  //		  for( i = j = 1; i < *pargc; i++, j++ )
  //		  {
  //			  /* Guaranteed to end because there are "*pargc" arguments left */
  //			  while ( argv[ j ] == NULL )
  //				  j++;
  //			  if ( i != j )
  //				  argv[ i ] = argv[ j ];
  //		  }
  //
  //	  #endif /* _WIN32_WCE */
  //
  //		  /*
  //		   * Have the display created now. If there wasn't a "-display"
  //		   * in the program arguments, we will use the DISPLAY environment
  //		   * variable for opening the X display (see code above):
  //		   */
  //		  fghInitialize( displayName );
  //		  /* will return true for VC8 (VC2005) and higher */
  //	  #if TARGET_HOST_MS_WINDOWS && ( _MSC_VER >= 1400 ) && HAVE_ERRNO
  //		  free ( displayName );  displayName = NULL;  /* dupenv_s allocates a string that we must free */
  //	  #endif
  //
  //		  /*
  //		   * Geometry parsing deffered until here because we may need the screen
  //		   * size.
  //		   */
  //
  //		  if (geometry )
  //		  {
  //			  unsigned int parsedWidth, parsedHeight;
  //			  int mask = XParseGeometry( geometry,
  //										 &fgState.Position.X, &fgState.Position.Y,
  //										 &parsedWidth, &parsedHeight );
  //			  /* TODO: Check for overflow? */
  //			  fgState.Size.X = parsedWidth;
  //			  fgState.Size.Y = parsedHeight;
  //
  //			  if( (mask & (WidthValue|HeightValue)) == (WidthValue|HeightValue) )
  //				  fgState.Size.Use = GL_TRUE;
  //
  //			  if( mask & XNegative )
  //				  fgState.Position.X += fgDisplay.ScreenWidth - fgState.Size.X;
  //
  //			  if( mask & YNegative )
  //				  fgState.Position.Y += fgDisplay.ScreenHeight - fgState.Size.Y;
  //
  //			  if( (mask & (XValue|YValue)) == (XValue|YValue) )
  //				  fgState.Position.Use = GL_TRUE;
  //		  }
  //	  }

  def glutInitDisplayMode(displayMode: Int) = fgState.DisplayMode = displayMode

  def glutInitWindowSize(width: Int, height: Int) {
    fgState.Size.X = width;
    fgState.Size.Y = height;

    fgState.Size.Use = (width > 0) && (height > 0)
  }

  /*
   * A call to this function makes us sure that the Display and Structure
   * subsystems have been properly initialized and are ready to be used
   */
  def FREEGLUT_EXIT_IF_NOT_INITIALISED(string: String) {
    if (!fgState.Initialised) {
      fgError(" ERROR:  Function <%s> called without first calling 'glutInit'.", string);
    }
  }

  def FREEGLUT_INTERNAL_ERROR_EXIT_IF_NOT_INITIALISED(string: String) {
    if (!fgState.Initialised) {
      fgError(" ERROR:  Internal <%s> function called without first calling 'glutInit'.", (string));
    }
  }

  def FREEGLUT_INTERNAL_ERROR_EXIT(cond: Boolean, string: String, function: String) {
    if (!(cond)) {
      fgError(" ERROR:  Internal error <%s> in function %s", string, function);
    }
  }

  def glutCreateWindow(title: String) {
    /* XXX GLUT does not exit; it simply calls "glutInit" quietly if the
     * XXX application has not already done so.  The "freeglut" community
     * XXX decided not to go this route (freeglut-developer e-mail from
     * XXX Steve Baker, 12/16/04, 4:22 PM CST, "Re: [Freeglut-developer]
     * XXX Desired 'freeglut' behaviour when there is no current window"
     */
    FREEGLUT_EXIT_IF_NOT_INITIALISED("glutCreateWindow");

    fgCreateWindow(title, fgState.Position.Use, fgState.Position.X, fgState.Position.Y, fgState.Size.Use, fgState.Size.X, fgState.Size.Y, false, false)
  }

  private[this] var finished = false
  private[this] var resizePending = false
  private[this] var dirty = true

  def isFinished(): Boolean = synchronized {
    finished
  }

  def setFinished(b: Boolean): Unit = synchronized {
    finished = b
  }

  def isResizePending(): Boolean = synchronized {
    resizePending
  }

  def setResizePending(b: Boolean): Unit = synchronized {
    resizePending = b
  }

  def isDirty(): Boolean = synchronized {
    dirty
  }

  def setDirty(b: Boolean): Unit = synchronized {
    dirty = b
  }

  object WindowHandler extends WindowAdapter with ComponentListener {
    var frame: JFrame = _
    var canvas: Canvas = _

    var reshapeFunc: (Int, Int) => Unit = (x: Int, y: Int) => ()
    var displayFunc: () => Unit = () => ()
    var specialFunc: (Int, Int, Int) => Unit = (key: Int, x: Int, y: Int) => ()
    var keyboardFunc: (Int, Int, Int) => Unit = (key: Int, x: Int, y: Int) => ()

    def componentHidden(e: ComponentEvent) {
      glutPostRedisplay
    }

    def componentMoved(e: ComponentEvent) {
      //	  println(e.getComponent().getClass().getName() + " --- Moved");
      //	  println(e)
    }

    def componentResized(e: ComponentEvent) {
      // This is the AWT Thread, can't allow glViewport called from here
      // Do it from the main loop
      setResizePending(true)
    }

    def componentShown(e: ComponentEvent) {
      glutPostRedisplay
    }

    override def windowIconified(e: WindowEvent) {
      if (e.getID == WindowEvent.WINDOW_DEICONIFIED)
        glutPostRedisplay
    }

    override def windowGainedFocus(e: WindowEvent) {
      canvas.requestFocus
      glutPostRedisplay
    }

    override def windowClosing(e: WindowEvent) {
      println("Closed")
      setFinished(true)
    }
  }

  def fgCreateWindow(title: String, positionUse: Boolean, x: Int, y: Int, sizeUse: Boolean, w: Int, h: Int, gameMode: Boolean, isMenu: Boolean) {
    WindowHandler.frame = new JFrame(title)
    WindowHandler.frame.setSize(w, h)
    WindowHandler.frame.setVisible(true)
    WindowHandler.frame.setLocation(x, y)

    initializeFrame(x, y, w, h, true)
  }

  private def initializeFrame(x: Int, y: Int, w: Int, h: Int, format: Boolean) {
    WindowHandler.canvas = new Canvas
    WindowHandler.frame.add(WindowHandler.canvas, BorderLayout.CENTER)
    WindowHandler.canvas.setPreferredSize(new Dimension(w, h))
    WindowHandler.frame.setResizable(false)
    WindowHandler.frame.pack()

    setWindowListeners()

    Display.setFullscreen(false)
    Display.setVSyncEnabled(false)
    Display.setParent(WindowHandler.canvas)
    //		Display.setDisplayMode(new DisplayMode(w, h))
    //		Display.setLocation(x, y)

    createDisplayContext(format)

    // Setup GL
    glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
    glViewport(0, 0, w, h)
  }

  private def setWindowListeners() {
    WindowHandler.frame.addComponentListener(WindowHandler)
    WindowHandler.frame.addWindowListener(WindowHandler)
    WindowHandler.frame.addWindowFocusListener(WindowHandler)
  }

  private def clearWindowListeners() {
    WindowHandler.frame.removeComponentListener(WindowHandler)
    WindowHandler.frame.removeWindowListener(WindowHandler)
    WindowHandler.frame.removeWindowFocusListener(WindowHandler)
  }

  private def createDisplayContext(format: Boolean) {
    if (format) {
      val flags = fgState.DisplayMode

      def flag(f: Int): Boolean = (flags & f) != 0

      var format = new PixelFormat
      if (flag(GLUT_DOUBLE)) format = format.withAuxBuffers(1)
      //			if (flag(GLUT_RGBA)) format = format.withBitsPerPixel(32)
      //			if (flag(GLUT_RGB)) format = format.withBitsPerPixel(32)
      if (flag(GLUT_DEPTH)) format = format.withDepthBits(24)
      if (flag(GLUT_STENCIL)) format = format.withStencilBits(8)

      Display.create(format)
    } else {
      Display.create()
    }
  }

  def glutReshapeFunc(func: (Int, Int) => Unit) {
    WindowHandler.reshapeFunc = func

    WindowHandler.reshapeFunc(WindowHandler.frame.getContentPane.getWidth, WindowHandler.frame.getContentPane.getHeight)
    setResizePending(false)
  }

  def glutDisplayFunc(func: () => Unit) {
    WindowHandler.displayFunc = func
  }

  def glutSpecialFunc(func: (Int, Int, Int) => Unit) {
    WindowHandler.specialFunc = func
  }

  def glutKeyboardFunc(func: (Int, Int, Int) => Unit) {
    WindowHandler.keyboardFunc = func
  }

  var lastRender = 0L

  private def keyboardToSpecialKeyGlut(key: Int) = {
    import org.lwjgl.input.Keyboard._
    key match {
      case KEY_F1 => GLUT_KEY_F1
      case KEY_F2 => GLUT_KEY_F2
      case KEY_F3 => GLUT_KEY_F3
      case KEY_F4 => GLUT_KEY_F4
      case KEY_F5 => GLUT_KEY_F5
      case KEY_F6 => GLUT_KEY_F6
      case KEY_F7 => GLUT_KEY_F7
      case KEY_F8 => GLUT_KEY_F8
      case KEY_F9 => GLUT_KEY_F9
      case KEY_F10 => GLUT_KEY_F10
      case KEY_F11 => GLUT_KEY_F11
      case KEY_F12 => GLUT_KEY_F12
      case KEY_LEFT => GLUT_KEY_LEFT
      case KEY_UP => GLUT_KEY_UP
      case KEY_RIGHT => GLUT_KEY_RIGHT
      case KEY_DOWN => GLUT_KEY_DOWN
      case KEY_PRIOR => GLUT_KEY_PAGE_UP // ????
      case KEY_NEXT => GLUT_KEY_PAGE_DOWN // ????
      case KEY_HOME => GLUT_KEY_HOME
      case KEY_END => GLUT_KEY_END
      case KEY_INSERT => GLUT_KEY_INSERT
      case _ => 0
    }
  }

  private def keyboardToKeyGlut(key: Int) = {
    import org.lwjgl.input.Keyboard._
    key match {
      case _ => 0
    }
  }

  def glutMainLoop() {
    if (!Display.isCreated) {
      println("Main Window not Initalized, exiting rendering loop.")
      return
    }

    // Rendering
    while (!isFinished && !Display.isCloseRequested()) {
      //			if (isResizePending()) {
      //				println("Rezising")
      //
      //				setResizePending(false)
      //				clearWindowListeners()
      ////
      //				val w = WindowHandler.frame.getWidth
      //				val h = WindowHandler.frame.getHeight
      //				val location = WindowHandler.frame.getLocation
      //
      //				// Re-create window, does not seem necesary, crash ocurrs anyway after several resizings
      //				Display.destroy()
      //
      //				WindowHandler.frame.remove(WindowHandler.canvas)
      //
      //				initializeFrame(location.x, location.y, w, h, false)
      //
      //				println("Reshaping to: [" + w + ", " + h + "]")
      //				WindowHandler.reshapeFunc(w, h)
      //
      //				setDirty(true)
      //
      //				setWindowListeners()
      //			}

      if (isDirty) {
        setDirty(false)

        WindowHandler.displayFunc()
        Display.update()
      } else {
        Display.processMessages() // update calls processMessages
      }

      while (Keyboard.next()) {
        if (Keyboard.getEventKeyState || Keyboard.isRepeatEvent) {
          // Is this what it's supposed to send???
          val keyEvent = Keyboard.getEventKey
          val key = keyboardToSpecialKeyGlut(keyEvent)
          if (key > 0) {
            WindowHandler.specialFunc(key, Mouse.getX, Mouse.getY)
          } else {
            WindowHandler.keyboardFunc(Keyboard.getEventCharacter.toInt, Mouse.getX, Mouse.getY)
          }
        }
      }
    }

    println("Exited Display loop")
    Display.destroy()
    WindowHandler.frame.dispose()
  }

  def glutSwapBuffers() {
    //	Display.swapBuffers // <- should not be called, called by Display.update()
  }

  def glutPostRedisplay() {
    setDirty(true)
  }

  def glutSetWindowTitle(title: String) {
    if (Display.isCreated)
      WindowHandler.frame.setTitle(title)
  }
}