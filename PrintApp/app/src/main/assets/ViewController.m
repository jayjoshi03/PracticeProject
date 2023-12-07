//
//  ViewController.m
//  BrotherDemo
//

#import "ViewController.h"
#import "BROTHER/BROTHERSDK.h"

@interface ViewController ()
@property (weak, nonatomic) IBOutlet UITextField *IP;
@property (weak, nonatomic) IBOutlet UITextField *Page;

@end

@implementation ViewController

BROTHERSDK *_lib;
int Index = 0;
NSString* absolutePath_pdf = @"";

- (void)viewDidLoad {
    [super viewDidLoad];
    _lib = [BROTHERSDK new];
    
    NSBundle* mainbundle = [NSBundle mainBundle];
    absolutePath_pdf = [mainbundle pathForResource:@"multipdf" ofType:@"pdf"];
    
    NSInteger TotalPage = [_lib getPDFPageCountbyPath:absolutePath_pdf];
    if(TotalPage >0)
    {
        NSString* pholder = @"1~";
        pholder = [pholder stringByAppendingString:[NSString stringWithFormat:@"%d", TotalPage]];
        pholder = [pholder stringByAppendingString:@", 0 = Print All"];
        _Page.placeholder = pholder;
    }
    else
    {
        _Page.placeholder = @"Invalid PDF path";
    }

}

- (void)parseIndex {
    
    NSCharacterSet* _num = [NSCharacterSet decimalDigitCharacterSet];
    NSCharacterSet* _input = [NSCharacterSet characterSetWithCharactersInString:_Page.text];
    
    
    if(_Page.text.length != 0)
    {
        if([_num isSupersetOfSet:_input])
        {
        Index = [_Page.text intValue];
        }
        else
        {
            _Page.text = @"0";
            Index = 0;
        }
    }
    else
    {
        _Page.text = @"0";
        Index = 0;
    }
}

- (IBAction)click_wifi:(id)sender {
    
    [_lib openport:_IP.text];

    [self parseIndex];
    [self cmd];
    
    [_lib closeport:1];
}

- (IBAction)click:(id)sender {

    [_lib openportMFI:@"com.issc.datapath"];
    
    [self parseIndex];
    [self cmd];
    
    [_lib closeport:10];
    
}

- (void) cmd{
    [_lib sendcommand:@"SIZE 100 mm, 60 mm\r\n"];
    if(Index == 0)
        [_lib printPDFbyPath:absolutePath_pdf x:0 y:0 printer_dpi:203];
    else
        [_lib printPDFbyPath:absolutePath_pdf x:0 y:0 printer_dpi:203 page_index:Index];
}


@end
