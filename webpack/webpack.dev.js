const webpack = require('webpack');
const writeFilePlugin = require('write-file-webpack-plugin');
const webpackMerge = require('webpack-merge');
const ForkTsCheckerWebpackPlugin = require('fork-ts-checker-webpack-plugin');
const FriendlyErrorsWebpackPlugin = require('friendly-errors-webpack-plugin');
const SimpleProgressWebpackPlugin = require('simple-progress-webpack-plugin');
const path = require('path');

const utils = require('./utils.js');
const commonIndexConfig = require('./webpack.common-index.js');
const commonConfig = require('./webpack.common.js');

const ENV = 'development';
const IMGPROXY = { url: 'http://localhost:9001' ,key: '3a8f347756fa5013430a1a3d0ebe2ad6', salt: '19b63d683008e7b88bb4427d9c0b45b3' };

module.exports = [
(options) => webpackMerge(commonIndexConfig({ env: ENV, imgproxy: IMGPROXY }), {
    devtool: 'eval-source-map',
    devServer: {
        contentBase: './build/www',
        proxy: [{
            context: [
                '/apis',
                '/management',
                '/swagger-resources',
                '/v2/api-docs',
                '/h2-console',
                '/auth'
            ],
            target: `http${options.tls ? 's' : ''}://127.0.0.1:8080`,
            secure: false,
            changeOrigin: options.tls,
            headers: { host: 'localhost:9000' }
        }],
        stats: options.stats,
        watchOptions: {
            ignored: /node_modules/
        }
    },
    entry: {
        polyfills: './src/main/webapp/index/app/polyfills',
        global: './src/main/webapp/index/content/css/global.css',
        main: './src/main/webapp/index/app/app.main'
    },
    output: {
        path: utils.root('build/www'),
        filename: 'index/app/[name].bundle.js',
        chunkFilename: 'index/app/[id].chunk.js'
    },
    module: {
        rules: [
            {
                test: /\.html$/,
                loader: 'html-loader',
                options: {
                    minimize: true,
                    caseSensitive: true,
                    removeAttributeQuotes:false,
                    minifyJS:false,
                    minifyCSS:false
                },
                exclude: /(src\/main\/webapp\/index.html)/
            },
            {
                test: /\.ts$/,
                enforce: 'pre',
                loader: 'tslint-loader',
                exclude: [/(node_modules)/, new RegExp('reflect-metadata\\' + path.sep + 'Reflect\\.ts')]
            },
            {
                test: /\.ts$/,
                use: [
                    'angular2-template-loader',
                    {
                        loader: 'cache-loader',
                        options: {
                            cacheDirectory: path.resolve('build/cache-loader')
                        }
                    },
                    {
                        loader: 'thread-loader',
                        options: {
                            // there should be 1 cpu for the fork-ts-checker-webpack-plugin
                            workers: require('os').cpus().length - 1
                        }
                    },
                    {
                        loader: 'ts-loader',
                        options: {
                            transpileOnly: true,
                            happyPackMode: true
                        }
                    },
                    'angular-router-loader'
                ],
                exclude: /(node_modules)/
            },
            {
                test: /\.css$/,
                use: ['to-string-loader', 'css-loader'],
                exclude: /(vendor\.css|global\.css)/
            },
            {
                test: /(vendor\.css|global\.css)/,
                use: ['style-loader', 'css-loader']
            }
        ]
    },
    stats: process.env.JHI_DISABLE_WEBPACK_LOGS ? 'none' : options.stats,
    plugins: [
        process.env.JHI_DISABLE_WEBPACK_LOGS
            ? null
            : new SimpleProgressWebpackPlugin({
                format: options.stats === 'minimal' ? 'compact' : 'expanded'
            }),
        new FriendlyErrorsWebpackPlugin(),
    	new ForkTsCheckerWebpackPlugin(),
        new webpack.ContextReplacementPlugin(
    		/angular(\\|\/)core(\\|\/)/,
    		path.resolve(__dirname, './src/main/webapp/index/')
		),
        new writeFilePlugin(),
        new webpack.WatchIgnorePlugin([
            utils.root('src/test'),
        ])
    ],
    mode: 'development'
}),
(options) => webpackMerge(commonConfig({ env: ENV, imgproxy: IMGPROXY, appName: 'accounts' }), {
    devtool: 'eval-source-map',
    devServer: {
        contentBase: './build/www',
        proxy: [{
            context: [
                '/apis',
                '/management',
                '/swagger-resources',
                '/v2/api-docs',
                '/h2-console',
                '/auth'
            ],
            target: `http${options.tls ? 's' : ''}://127.0.0.1:8080`,
            secure: false,
            changeOrigin: options.tls,
            headers: { host: 'localhost:9000' }
        }],
        stats: options.stats,
        watchOptions: {
            ignored: /node_modules/
        }
    },
    entry: {
        polyfills: './src/main/webapp/accounts/app/polyfills',
        global: './src/main/webapp/accounts/content/css/global.css',
        main: './src/main/webapp/accounts/app/app.main'
    },
    output: {
        path: utils.root('build/www/accounts'),
        filename: 'app/[name].bundle.js',
        chunkFilename: 'app/[id].chunk.js'
    },
    module: {
        rules: [
            {
                test: /\.html$/,
                loader: 'html-loader',
                options: {
                    minimize: true,
                    caseSensitive: true,
                    removeAttributeQuotes:false,
                    minifyJS:false,
                    minifyCSS:false
                },
                exclude: /(src\/main\/webapp\/accounts\/index.html)/
            },
            {
                test: /\.ts$/,
                enforce: 'pre',
                loader: 'tslint-loader',
                exclude: [/(node_modules)/, new RegExp('reflect-metadata\\' + path.sep + 'Reflect\\.ts')]
            },
            {
                test: /\.ts$/,
                use: [
                    'angular2-template-loader',
                    {
                        loader: 'cache-loader',
                        options: {
                            cacheDirectory: path.resolve('build/cache-loader')
                        }
                    },
                    {
                        loader: 'thread-loader',
                        options: {
                            // there should be 1 cpu for the fork-ts-checker-webpack-plugin
                            workers: require('os').cpus().length - 1
                        }
                    },
                    {
                        loader: 'ts-loader',
                        options: {
                            transpileOnly: true,
                            happyPackMode: true
                        }
                    },
                    'angular-router-loader'
                ],
                exclude: /(node_modules)/
            },
            {
                test: /\.css$/,
                use: ['to-string-loader', 'css-loader'],
                exclude: /(vendor\.css|global\.css)/
            },
            {
                test: /(vendor\.css|global\.css)/,
                use: ['style-loader', 'css-loader']
            }
        ]
    },
    stats: process.env.JHI_DISABLE_WEBPACK_LOGS ? 'none' : options.stats,
    plugins: [
        process.env.JHI_DISABLE_WEBPACK_LOGS
            ? null
            : new SimpleProgressWebpackPlugin({
                format: options.stats === 'minimal' ? 'compact' : 'expanded'
            }),
        new FriendlyErrorsWebpackPlugin(),
    	new ForkTsCheckerWebpackPlugin(),
        new webpack.ContextReplacementPlugin(
    		/angular(\\|\/)core(\\|\/)/,
    		path.resolve(__dirname, './src/main/webapp/accounts/')
		),
        new writeFilePlugin(),
        new webpack.WatchIgnorePlugin([
            utils.root('src/test'),
        ])
    ],
    mode: 'development'
}),
(options) => webpackMerge(commonConfig({ env: ENV, imgproxy: IMGPROXY, appName: 'blogs' }), {
    devtool: 'eval-source-map',
    devServer: {
        contentBase: './build/www',
        proxy: [{
            context: [
                '/apis',
                '/management',
                '/swagger-resources',
                '/v2/api-docs',
                '/h2-console',
                '/auth'
            ],
            target: `http${options.tls ? 's' : ''}://127.0.0.1:8080`,
            secure: false,
            changeOrigin: options.tls,
            headers: { host: 'localhost:9000' }
        }],
        stats: options.stats,
        watchOptions: {
            ignored: /node_modules/
        }
    },
    entry: {
        polyfills: './src/main/webapp/blogs/app/polyfills',
        global: './src/main/webapp/blogs/content/css/global.css',
        main: './src/main/webapp/blogs/app/app.main'
    },
    output: {
        path: utils.root('build/www/blogs'),
        filename: 'app/[name].bundle.js',
        chunkFilename: 'app/[id].chunk.js'
    },
    module: {
        rules: [
            {
                test: /\.html$/,
                loader: 'html-loader',
                options: {
                    minimize: true,
                    caseSensitive: true,
                    removeAttributeQuotes:false,
                    minifyJS:false,
                    minifyCSS:false
                },
                exclude: /(src\/main\/webapp\/blogs\/index.html)/
            },
            {
                test: /\.ts$/,
                enforce: 'pre',
                loader: 'tslint-loader',
                exclude: [/(node_modules)/, new RegExp('reflect-metadata\\' + path.sep + 'Reflect\\.ts')]
            },
            {
                test: /\.ts$/,
                use: [
                    'angular2-template-loader',
                    {
                        loader: 'cache-loader',
                        options: {
                            cacheDirectory: path.resolve('build/cache-loader')
                        }
                    },
                    {
                        loader: 'thread-loader',
                        options: {
                            // there should be 1 cpu for the fork-ts-checker-webpack-plugin
                            workers: require('os').cpus().length - 1
                        }
                    },
                    {
                        loader: 'ts-loader',
                        options: {
                            transpileOnly: true,
                            happyPackMode: true
                        }
                    },
                    'angular-router-loader'
                ],
                exclude: /(node_modules)/
            },
            {
                test: /\.css$/,
                use: ['to-string-loader', 'css-loader'],
                exclude: /(vendor\.css|global\.css)/
            },
            {
                test: /(vendor\.css|global\.css)/,
                use: ['style-loader', 'css-loader']
            }
        ]
    },
    stats: process.env.JHI_DISABLE_WEBPACK_LOGS ? 'none' : options.stats,
    plugins: [
        process.env.JHI_DISABLE_WEBPACK_LOGS
            ? null
            : new SimpleProgressWebpackPlugin({
                format: options.stats === 'minimal' ? 'compact' : 'expanded'
            }),
        new FriendlyErrorsWebpackPlugin(),
    	new ForkTsCheckerWebpackPlugin(),
        new webpack.ContextReplacementPlugin(
    		/angular(\\|\/)core(\\|\/)/,
    		path.resolve(__dirname, './src/main/webapp/blogs/')
		),
        new writeFilePlugin(),
        new webpack.WatchIgnorePlugin([
            utils.root('src/test'),
        ])
    ],
    mode: 'development'
}),
(options) => webpackMerge(commonConfig({ env: ENV, imgproxy: IMGPROXY, appName: 'drives' }), {
    devtool: 'eval-source-map',
    devServer: {
        contentBase: './build/www',
        proxy: [{
            context: [
                '/apis',
                '/management',
                '/swagger-resources',
                '/v2/api-docs',
                '/h2-console',
                '/auth'
            ],
            target: `http${options.tls ? 's' : ''}://127.0.0.1:8080`,
            secure: false,
            changeOrigin: options.tls,
            headers: { host: 'localhost:9000' }
        }],
        stats: options.stats,
        watchOptions: {
            ignored: /node_modules/
        }
    },
    entry: {
        polyfills: './src/main/webapp/drives/app/polyfills',
        global: './src/main/webapp/drives/content/css/global.css',
        main: './src/main/webapp/drives/app/app.main'
    },
    output: {
        path: utils.root('build/www/drives'),
        filename: 'app/[name].bundle.js',
        chunkFilename: 'app/[id].chunk.js'
    },
    module: {
        rules: [
            {
                test: /\.html$/,
                loader: 'html-loader',
                options: {
                    minimize: true,
                    caseSensitive: true,
                    removeAttributeQuotes:false,
                    minifyJS:false,
                    minifyCSS:false
                },
                exclude: /(src\/main\/webapp\/drives\/index.html)/
            },
            {
                test: /\.ts$/,
                enforce: 'pre',
                loader: 'tslint-loader',
                exclude: [/(node_modules)/, new RegExp('reflect-metadata\\' + path.sep + 'Reflect\\.ts')]
            },
            {
                test: /\.ts$/,
                use: [
                    'angular2-template-loader',
                    {
                        loader: 'cache-loader',
                        options: {
                            cacheDirectory: path.resolve('build/cache-loader')
                        }
                    },
                    {
                        loader: 'thread-loader',
                        options: {
                            // there should be 1 cpu for the fork-ts-checker-webpack-plugin
                            workers: require('os').cpus().length - 1
                        }
                    },
                    {
                        loader: 'ts-loader',
                        options: {
                            transpileOnly: true,
                            happyPackMode: true
                        }
                    },
                    'angular-router-loader'
                ],
                exclude: /(node_modules)/
            },
            {
                test: /\.css$/,
                use: ['to-string-loader', 'css-loader'],
                exclude: /(vendor\.css|global\.css)/
            },
            {
                test: /(vendor\.css|global\.css)/,
                use: ['style-loader', 'css-loader']
            }
        ]
    },
    stats: process.env.JHI_DISABLE_WEBPACK_LOGS ? 'none' : options.stats,
    plugins: [
        process.env.JHI_DISABLE_WEBPACK_LOGS
            ? null
            : new SimpleProgressWebpackPlugin({
                format: options.stats === 'minimal' ? 'compact' : 'expanded'
            }),
        new FriendlyErrorsWebpackPlugin(),
        new ForkTsCheckerWebpackPlugin(),
        new webpack.ContextReplacementPlugin(
            /angular(\\|\/)core(\\|\/)/,
            path.resolve(__dirname, './src/main/webapp/drives/')
        ),
        new writeFilePlugin(),
        new webpack.WatchIgnorePlugin([
            utils.root('src/test'),
        ])
    ],
    mode: 'development'
}),
(options) => webpackMerge(commonConfig({ env: ENV, imgproxy: IMGPROXY, appName: 'admin' }), {
    devtool: 'eval-source-map',
    devServer: {
        contentBase: './build/www',
        proxy: [{
            context: [
                '/apis',
                '/management',
                '/swagger-resources',
                '/v2/api-docs',
                '/h2-console',
                '/auth'
            ],
            target: `http${options.tls ? 's' : ''}://127.0.0.1:8080`,
            secure: false,
            changeOrigin: options.tls,
            headers: { host: 'localhost:9000' }
        }],
        stats: options.stats,
        watchOptions: {
            ignored: /node_modules/
        }
    },
    entry: {
        polyfills: './src/main/webapp/admin/app/polyfills',
        global: './src/main/webapp/admin/content/css/global.css',
        main: './src/main/webapp/admin/app/app.main'
    },
    output: {
        path: utils.root('build/www/admin'),
        filename: 'app/[name].bundle.js',
        chunkFilename: 'app/[id].chunk.js'
    },
    module: {
        rules: [
            {
                test: /\.html$/,
                loader: 'html-loader',
                options: {
                    minimize: true,
                    caseSensitive: true,
                    removeAttributeQuotes:false,
                    minifyJS:false,
                    minifyCSS:false
                },
                exclude: /(src\/main\/webapp\/admin\/index.html)/
            },
            {
                test: /\.ts$/,
                enforce: 'pre',
                loader: 'tslint-loader',
                exclude: [/(node_modules)/, new RegExp('reflect-metadata\\' + path.sep + 'Reflect\\.ts')]
            },
            {
                test: /\.ts$/,
                use: [
                    'angular2-template-loader',
                    {
                        loader: 'cache-loader',
                        options: {
                            cacheDirectory: path.resolve('build/cache-loader')
                        }
                    },
                    {
                        loader: 'thread-loader',
                        options: {
                            // there should be 1 cpu for the fork-ts-checker-webpack-plugin
                            workers: require('os').cpus().length - 1
                        }
                    },
                    {
                        loader: 'ts-loader',
                        options: {
                            transpileOnly: true,
                            happyPackMode: true
                        }
                    },
                    'angular-router-loader'
                ],
                exclude: /(node_modules)/
            },
            {
                test: /\.css$/,
                use: ['to-string-loader', 'css-loader'],
                exclude: /(vendor\.css|global\.css)/
            },
            {
                test: /(vendor\.css|global\.css)/,
                use: ['style-loader', 'css-loader']
            }
        ]
    },
    stats: process.env.JHI_DISABLE_WEBPACK_LOGS ? 'none' : options.stats,
    plugins: [
        process.env.JHI_DISABLE_WEBPACK_LOGS
            ? null
            : new SimpleProgressWebpackPlugin({
                format: options.stats === 'minimal' ? 'compact' : 'expanded'
            }),
        new FriendlyErrorsWebpackPlugin(),
    	new ForkTsCheckerWebpackPlugin(),
        new webpack.ContextReplacementPlugin(
    		/angular(\\|\/)core(\\|\/)/,
    		path.resolve(__dirname, './src/main/webapp/admin/')
		),
        new writeFilePlugin(),
        new webpack.WatchIgnorePlugin([
            utils.root('src/test'),
        ])
    ],
    mode: 'development'
})];
