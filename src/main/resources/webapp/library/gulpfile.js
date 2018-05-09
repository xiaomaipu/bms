var gulp = require('gulp')
var fs = require('fs')
var sass = require('gulp-sass')
var rename = require('gulp-rename')
var babel = require('gulp-babel')
var imagemin = require('gulp-imagemin');
var clean = require('gulp-clean');
gulp.task('clean', function() {
    gulp.src('./dist')
        .pipe(clean())
})
gulp.task('wxml', function() {
    gulp.src('./src/**/*.html')
        .pipe(rename(function(path) {
            path.extname = '.wxml'
        }))
        .pipe(gulp.dest('dist/'))
})
gulp.task('sass', function() {
    gulp.src('./src/**/*.scss')
        .pipe(sass({
            outputStyle: 'compressed'
        }))
        .pipe(rename(function(path) {
            path.extname = '.wxss'
        }))
        .pipe(gulp.dest('dist'))
})
gulp.task('json', function() {
    gulp.src('./src/**/*.json')
        // .pipe(yaml({ space: 2 }))
        .pipe(gulp.dest('dist'))
})

gulp.task('js', function() {
    gulp.src('./src/**/*.js')
        // .pipe(babel({
        //     presets: ['es2015']
        // }))
        .pipe(gulp.dest('dist'))
})

gulp.task('wxs', function() {
    gulp.src('./src/**/*.wxs')
        // .pipe(babel({
        //     presets: ['es2015']
        // }))
        .pipe(gulp.dest('dist'))
})

gulp.task('image', function() {
    gulp.src('./src/**/*.{png,jpg,gif,ico}')
        .pipe(imagemin({
            interlaced: true, //隔行扫描压缩jqp图片
            optimizationLevel: 5, //0-7
            progressive: true, //无损压缩jpg
            multipass: true //多次优化svg直到最优
        }))
        .pipe(gulp.dest('dist'))
})

gulp.task('default', ['wxml', 'sass', 'json', 'js', "image", "wxs"], function() {

    gulp.watch('./src/**/*.html', ['wxml'])
    gulp.watch('./src/**/*.scss', ['sass'])
    gulp.watch('./src/**/*.json', ['json'])
    gulp.watch('./src/**/*.js', ['js'])
    gulp.watch('./src/**/*.wxs', ['wxs'])
})