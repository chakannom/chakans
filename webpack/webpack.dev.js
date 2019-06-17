const webpack = require('webpack');
const { BaseHrefWebpackPlugin } = require('base-href-webpack-plugin');
const writeFilePlugin = require('write-file-webpack-plugin');
const webpackMerge = require('webpack-merge');
const BrowserSyncPlugin = require('browser-sync-webpack-plugin');
const ForkTsCheckerWebpackPlugin = require('fork-ts-checker-webpack-plugin');
const FriendlyErrorsWebpackPlugin = require('friendly-errors-webpack-plugin');
const SimpleProgressWebpackPlugin = require('simple-progress-webpack-plugin');
const path = require('path');
const sass = require('sass');

const utils = require('./utils.js');
const commonIndexAppConfig = require('./webpack.common.index-app.js');
const commonNonIndexAppConfig = require('./webpack.common.non-index-app.js');

const ENV = 'development';
const IMGPROXY = { url: 'http://localhost:9901' ,key: '3a8f347756fa5013430a1a3d0ebe2ad6', salt: '19b63d683008e7b88bb4427d9c0b45b3' };

module.exports = [
    // Index
    (options) => webpackMerge(commonIndexAppConfig({ env: ENV, imgproxy: IMGPROXY }), {
        devtool: 'eval-source-map',
        devServer: {
            contentBase: './build/resources/main/static/',
            proxy: [{
                context: [
                    '/'
                ],
                target: `http${options.tls ? 's' : ''}://localhost:8080`,
                secure: false,
                changeOrigin: options.tls
            }],
            stats: options.stats,
            watchOptions: {
                ignored: /node_modules/
            },
            https: options.tls,
            historyApiFallback: true
        },
        entry: {
            polyfills: './src/main/webapp/index/app/polyfills',
            global: './src/main/webapp/index/content/scss/global.scss',
            main: './src/main/webapp/index/app/app.main'
        },
        output: {
            path: utils.root('build/resources/main/static/'),
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
                              cacheDirectory: path.resolve('build/cache-loader/index')
                            }
                        },
                        {
                            loader: 'thread-loader',
                            options: {
                                // There should be 1 cpu for the fork-ts-checker-webpack-plugin.
                                // The value may need to be adjusted (e.g. to 1) in some CI environments,
                                // as cpus() may report more cores than what are available to the build.
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
                    test: /\.scss$/,
                    use: ['to-string-loader', 'css-loader', {
                        loader: 'sass-loader',
                        options: { implementation: sass }
                    }],
                    exclude: /(vendor\.scss|global\.scss)/
                },
                {
                    test: /(vendor\.scss|global\.scss)/,
                    use: ['style-loader', 'css-loader', 'postcss-loader', {
                        loader: 'sass-loader',
                        options: { implementation: sass }
                    }]
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
            ]),
            new BaseHrefWebpackPlugin({ baseHref: '/' })
        ].filter(Boolean),
        mode: 'development'
    }),
    // Accounts
    (options) => webpackMerge(commonNonIndexAppConfig({ env: ENV, imgproxy: IMGPROXY, appName: 'accounts' }), {
        devtool: 'eval-source-map',
        devServer: {
            contentBase: './build/resources/main/static/',
            proxy: [{
                context: [
                    '/'
                ],
                target: `http${options.tls ? 's' : ''}://localhost:8080`,
                secure: false,
                changeOrigin: options.tls
            }],
            stats: options.stats,
            watchOptions: {
                ignored: /node_modules/
            },
            https: options.tls,
            historyApiFallback: true
        },
        entry: {
            polyfills: './src/main/webapp/accounts/app/polyfills',
            global: './src/main/webapp/accounts/content/scss/global.scss',
            main: './src/main/webapp/accounts/app/app.main'
        },
        output: {
            path: utils.root('build/resources/main/static/accounts/'),
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
                              cacheDirectory: path.resolve('build/cache-loader/accounts')
                            }
                        },
                        {
                            loader: 'thread-loader',
                            options: {
                                // There should be 1 cpu for the fork-ts-checker-webpack-plugin.
                                // The value may need to be adjusted (e.g. to 1) in some CI environments,
                                // as cpus() may report more cores than what are available to the build.
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
                    test: /\.scss$/,
                    use: ['to-string-loader', 'css-loader', {
                        loader: 'sass-loader',
                        options: { implementation: sass }
                    }],
                    exclude: /(vendor\.scss|global\.scss)/
                },
                {
                    test: /(vendor\.scss|global\.scss)/,
                    use: ['style-loader', 'css-loader', 'postcss-loader', {
                        loader: 'sass-loader',
                        options: { implementation: sass }
                    }]
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
            ]),
            new BaseHrefWebpackPlugin({ baseHref: '/accounts/' })
        ].filter(Boolean),
        mode: 'development'
    }),
    // Blogs
    (options) => webpackMerge(commonNonIndexAppConfig({ env: ENV, imgproxy: IMGPROXY, appName: 'blogs' }), {
        devtool: 'eval-source-map',
        devServer: {
            contentBase: './build/resources/main/static/',
            proxy: [{
                context: [
                    '/'
                ],
                target: `http${options.tls ? 's' : ''}://localhost:8080`,
                secure: false,
                changeOrigin: options.tls
            }],
            stats: options.stats,
            watchOptions: {
                ignored: /node_modules/
            },
            https: options.tls,
            historyApiFallback: true
        },
        entry: {
            polyfills: './src/main/webapp/blogs/app/polyfills',
            global: './src/main/webapp/blogs/content/scss/global.scss',
            main: './src/main/webapp/blogs/app/app.main'
        },
        output: {
            path: utils.root('build/resources/main/static/blogs/'),
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
                                cacheDirectory: path.resolve('build/cache-loader/blogs')
                            }
                        },
                        {
                            loader: 'thread-loader',
                            options: {
                                // There should be 1 cpu for the fork-ts-checker-webpack-plugin.
                                // The value may need to be adjusted (e.g. to 1) in some CI environments,
                                // as cpus() may report more cores than what are available to the build.
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
                    test: /\.scss$/,
                    use: ['to-string-loader', 'css-loader', {
                        loader: 'sass-loader',
                        options: { implementation: sass }
                    }],
                    exclude: /(vendor\.scss|global\.scss)/
                },
                {
                    test: /(vendor\.scss|global\.scss)/,
                    use: ['style-loader', 'css-loader', 'postcss-loader', {
                        loader: 'sass-loader',
                        options: { implementation: sass }
                    }]
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
            ]),
            new BaseHrefWebpackPlugin({ baseHref: '/blogs/' })
        ].filter(Boolean),
        mode: 'development'
    }),
    // Admin
    (options) => webpackMerge(commonNonIndexAppConfig({ env: ENV, imgproxy: IMGPROXY, appName: 'admin' }), {
        devtool: 'eval-source-map',
        devServer: {
            contentBase: './build/resources/main/static/',
            proxy: [{
                context: [
                    '/'
                ],
                target: `http${options.tls ? 's' : ''}://localhost:8080`,
                secure: false,
                changeOrigin: options.tls
            }],
            stats: options.stats,
            watchOptions: {
                ignored: /node_modules/
            },
            https: options.tls,
            historyApiFallback: true
        },
        entry: {
            polyfills: './src/main/webapp/admin/app/polyfills',
            global: './src/main/webapp/admin/content/scss/global.scss',
            main: './src/main/webapp/admin/app/app.main'
        },
        output: {
            path: utils.root('build/resources/main/static/admin/'),
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
                              cacheDirectory: path.resolve('build/cache-loader/admin')
                            }
                        },
                        {
                            loader: 'thread-loader',
                            options: {
                                // There should be 1 cpu for the fork-ts-checker-webpack-plugin.
                                // The value may need to be adjusted (e.g. to 1) in some CI environments,
                                // as cpus() may report more cores than what are available to the build.
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
                    test: /\.scss$/,
                    use: ['to-string-loader', 'css-loader', {
                        loader: 'sass-loader',
                        options: { implementation: sass }
                    }],
                    exclude: /(vendor\.scss|global\.scss)/
                },
                {
                    test: /(vendor\.scss|global\.scss)/,
                    use: ['style-loader', 'css-loader', 'postcss-loader', {
                        loader: 'sass-loader',
                        options: { implementation: sass }
                    }]
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
            new BrowserSyncPlugin({
                https: options.tls,
                host: 'localhost',
                port: 9000,
                proxy: {
                    target: `http${options.tls ? 's' : ''}://localhost:9060`,
                    proxyOptions: {
                        changeOrigin: false  //pass the Host header to the backend unchanged  https://github.com/Browsersync/browser-sync/issues/430
                    }
                },
                socket: {
                    clients: {
                        heartbeatTimeout: 60000
                    }
                }
            }, {
                reload: false
            }),
            new webpack.ContextReplacementPlugin(
                /angular(\\|\/)core(\\|\/)/,
                path.resolve(__dirname, './src/main/webapp/admin/')
            ),
            new writeFilePlugin(),
            new webpack.WatchIgnorePlugin([
                utils.root('src/test'),
            ]),
            new BaseHrefWebpackPlugin({ baseHref: '/admin/' })
        ].filter(Boolean),
        mode: 'development'
    })
];
