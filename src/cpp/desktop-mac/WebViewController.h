

#import <Cocoa/Cocoa.h>
#import <Webkit/WebKit.h>

@interface WebViewController : NSWindowController<NSWindowDelegate> {
   WebView* webView_;
   NSString* name_;
   NSURL* baseUrl_;
   NSString* viewerUrl_;
}

+ (WebViewController*) windowNamed: (NSString*) name;

+ (void) activateSatelliteWindow: (NSString*) name;

+ (void) prepareForSatelliteWindow: (NSString*) name
                             width: (int) width
                            height: (int) height;


// The designated initializer
- (id)initWithURLRequest: (NSURLRequest*) request
                    name: (NSString*) name;

// load a new url
- (void) loadURL: (NSURL*) url;

// set the current viewer url
- (void) setViewerURL: (NSString*) url;

// Get the embedded WebView
- (WebView*) webView;

// subclass methods for registering javascript callbacks
- (void) registerDesktopObject;
@end
