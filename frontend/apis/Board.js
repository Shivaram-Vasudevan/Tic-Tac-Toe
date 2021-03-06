import { getPlayerSymbolEnum, getPlayerSymbol } from '../constants/Player_Symbol_Enum';

export default async function getNextBoard(board, playerSymbol) {
    let boardSubstitutedWithPlayerSymbolEnum = board.map(element => getPlayerSymbolEnum(element, playerSymbol));

    let boardObject = encodeURIComponent(JSON.stringify({board: boardSubstitutedWithPlayerSymbolEnum, boardSize: 3}));
    const url = 'http://localhost:8080/nextMove?board=' + boardObject;
    
    const nextMove = await fetch(url, {method: 'GET'})
                        .then(data => data.json())
                        .then(data => data['board']);
    return nextMove.map(symbolEnum => getPlayerSymbol(symbolEnum, playerSymbol));
}