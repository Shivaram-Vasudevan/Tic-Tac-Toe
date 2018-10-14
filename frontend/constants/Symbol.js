const symbol = {
    X: 'X',
    O: 'O'
}

export default symbol;

export function toggleSymbol(playerSymbol) {
    if (playerSymbol == symbol.X){
        return symbol.O;
    }
    else if (playerSymbol == symbol.O) {
        return symbol.X;
    }
    return undefined;
}