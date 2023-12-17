import Foundation

let path = "./input/d17.txt"

do {
    let data = try NSString(contentsOfFile: path, encoding: String.Encoding.ascii.rawValue)

    print(data)
} catch {
    print("Error reading file: \(error)")
}
