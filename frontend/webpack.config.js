var config = {
   entry: './main.js',
   mode: 'production',
   output: {
      path:'/',
      filename: 'index.js',
   },
   devServer: {
      inline: true,
      port: 8090
   },
   module: {
      rules: [
         {
            test: /\.jsx?$/,
            exclude: /node_modules/,
            loader: "babel-loader",
            query: {
               presets: ['es2015', 'react']
            }
         }
      ]
   }
}
module.exports = config;