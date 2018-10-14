const PLAYER_SYMBOL_MAP = {
    'EMPTY' : 'E', 
    'PLAYER' : 'P', 
    'COMPUTER' : 'C'
};

export function getPlayerSymbolEnum(element, symbolForPlayer) {
    if (element == symbolForPlayer) {
        return PLAYER_SYMBOL_MAP['PLAYER'];
    }
    if (element == null) {
        return PLAYER_SYMBOL_MAP['EMPTY'];
    }
    return PLAYER_SYMBOL_MAP['COMPUTER'];
}

export function getPlayerSymbol(playerSymbolEnum, playerSymbol) {
    if (playerSymbolEnum == PLAYER_SYMBOL_MAP['PLAYER']) {
        return playerSymbol;
    }
    if (playerSymbolEnum == PLAYER_SYMBOL_MAP['EMPTY']) {
        return null;
    }
    return playerSymbol == 'X' ? 'O' : 'X';
}